import torch
import torch.nn as nn
import numpy as np
import pandas as pd
import sys
import os
import json

CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))

# 필요 없으면 아래 두 줄 제거 가능
UTILS_DIR = os.path.abspath(os.path.join(CURRENT_DIR, '..', 'utils'))
sys.path.insert(0, UTILS_DIR)
# from my_formatter import format_prediction_output  # 필요 없으면 주석 처리 또는 삭제

# 지역명 → areacode 매핑
area_mapping = {
    '서울': 1, '인천': 2, '대전': 3, '대구': 4, '광주': 5, '부산': 6, '울산': 7,
    '세종': 8, '경기': 31, '강원': 32, '충북': 33, '충남': 34, '경북': 35,
    '경남': 36, '전북': 37, '전남': 38, '제주': 39
}

class MLP(nn.Module):
    def __init__(self, input_dim, hidden_dim, output_dim):
        super(MLP, self).__init__()
        self.model = nn.Sequential(
            nn.Linear(input_dim, hidden_dim),
            nn.ReLU(),
            nn.Linear(hidden_dim, hidden_dim),
            nn.ReLU(),
            nn.Linear(hidden_dim, output_dim)
        )

    def forward(self, x):
        return self.model(x)

def get_season_from_date(date_str):
    month = pd.to_datetime(date_str).month
    if 3 <= month <= 5:
        return 0
    elif 6 <= month <= 8:
        return 1
    elif 9 <= month <= 11:
        return 2
    else:
        return 3

def get_days_between(start_date, end_date):
    start = pd.to_datetime(start_date)
    end = pd.to_datetime(end_date)
    return (end - start).days + 1

def load_poi_data():
    base_dir = os.path.dirname(os.path.abspath(__file__))
    poi_path = os.path.abspath(os.path.join(base_dir, '..', 'data', 'poi.csv'))
    if not os.path.exists(poi_path):
        raise FileNotFoundError(f"POI 데이터 파일이 존재하지 않습니다: {poi_path}")
    poi = pd.read_csv(poi_path, encoding='utf-8-sig')
    return poi

def filter_poi_by_place(poi_data, place_name):
    area_code = area_mapping.get(place_name)
    if area_code is None:
        raise ValueError(f"입력한 지역명 '{place_name}'은 지원되지 않습니다.")
    filtered = poi_data[poi_data['areacode'] == area_code].reset_index(drop=True)
    return filtered

def predict_satisfaction_and_recommend(user_input):
    tag_mapping = {
        "1": "문화", "2": "역사", "3": "자연", "4": "체험", "5": "기타",
        "6": "레저", "7": "축제", "8": "음식", "9": "쇼핑", "10": "전통",
        "11": "예술", "12": "기타2"
    }

    poi_data = load_poi_data()
    filtered_poi = filter_poi_by_place(poi_data, user_input['place'])

    filtered_poi['tag'] = filtered_poi['tag'].fillna('').astype(str)
    filtered_poi['tag'] = filtered_poi['tag'].map(tag_mapping)

    tourist_poi = filtered_poi[(filtered_poi['tag'] != '음식') & (filtered_poi['tag'] != 'None')]
    food_poi = filtered_poi[filtered_poi['tag'] == '음식']

    all_tags = list(range(1, 13))
    selected_tags = list(map(int, user_input['travel_type'].split(';')))
    travel_type_encoded = [1 if tag in selected_tags else 0 for tag in all_tags]

    rain_dict = user_input['rain_rate']
    if isinstance(rain_dict, dict):
        rain_values = list(rain_dict.values())
        rain_avg = sum(rain_values) / len(rain_values) if rain_values else 0.0
    else:
        rain_avg = float(rain_dict)

    is_outdoor = 1.0 - rain_avg
    season = get_season_from_date(user_input['start_date'])

    feature_list = [
                       user_input['gender'],
                       user_input['age'],
                       season,
                       user_input['means_tp'],
                       user_input['person'],
                       is_outdoor
                   ] + travel_type_encoded

    feature_array = np.array(feature_list, dtype='float32')
    feature_array = np.nan_to_num(feature_array, nan=-999.0)
    X_tensor = torch.tensor(feature_array, dtype=torch.float32).unsqueeze(0)

    model = MLP(input_dim=X_tensor.shape[1], hidden_dim=128, output_dim=1)

    model_path = os.path.abspath(os.path.join(CURRENT_DIR, '..', 'model', 'model_ver7.pth'))
    if not os.path.exists(model_path):
        raise FileNotFoundError(f"모델 파일이 존재하지 않습니다: {model_path}")

    model.load_state_dict(torch.load(model_path, map_location='cpu'))
    model.eval()

    dates = pd.date_range(user_input['start_date'], user_input['end_date']).strftime('%Y-%m-%d').tolist()

    courses = []
    for _ in range(3):
        schedule = {}
        for date in dates:
            selected_titles = []
            tourist_samples = tourist_poi.sample(n=min(3, len(tourist_poi)), replace=True)
            selected_titles.extend(
                tourist_samples[['title', 'mapx', 'mapy', 'addr', 'tag']].to_dict(orient='records')
            )
            food_samples = food_poi.sample(n=min(2, len(food_poi)), replace=True)
            food_titles = food_samples[['title', 'mapx', 'mapy', 'addr', 'tag']].to_dict(orient='records')
            day_titles = [selected_titles[0], food_titles[0], selected_titles[1], selected_titles[2], food_titles[1]]
            schedule[date] = day_titles
        courses.append({'area_name': user_input['place'], 'schedule': schedule})

    return clean_nan(courses)

def clean_nan(obj):
    if isinstance(obj, float) and np.isnan(obj):
        return None
    elif isinstance(obj, dict):
        return {k: clean_nan(v) for k, v in obj.items()}
    elif isinstance(obj, list):
        return [clean_nan(x) for x in obj]
    else:
        return obj

def main():
    input_json = sys.stdin.read()
    try:
        user_input = json.loads(input_json)
    except json.JSONDecodeError as e:
        print(json.dumps({"error": f"Invalid JSON input: {str(e)}"}))
        sys.exit(1)

    result = predict_satisfaction_and_recommend(user_input)

    print(json.dumps(result, ensure_ascii=False, indent=4), flush=True)

if __name__ == "__main__":
    main()
