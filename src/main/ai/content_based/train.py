import pandas as pd
import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader, TensorDataset
import sys
import os
import pymysql
from datetime import datetime
import numpy as np
import re
import json

# 경로 설정
CURRENT_DIR = os.path.dirname(os.path.abspath(__file__))
UTILS_DIR = os.path.abspath(os.path.join(CURRENT_DIR, '..', 'utils'))
sys.path.insert(0, UTILS_DIR)

from my_preprocessing import get_preprocessed_data

# 모델 정의
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

# 멀티핫 인코딩

def fixed_multi_hot_encode(df, column, valid_tags):
    for tag in valid_tags:
        df[f"{column}_{tag}"] = df[column].apply(
            lambda x: 1 if isinstance(x, str) and str(tag) in x.split(';') else 0
        )
    return df

# 모델 경로 생성 함수

def get_next_model_version(save_dir: str, prefix: str = "model_ver", ext: str = ".pth") -> str:
    os.makedirs(save_dir, exist_ok=True)
    existing = os.listdir(save_dir)
    pattern = re.compile(f"{prefix}(\\d+){ext}")
    versions = [int(pattern.match(f).group(1)) for f in existing if pattern.match(f)]
    next_ver = max(versions) + 1 if versions else 1
    return os.path.join(save_dir, f"{prefix}{next_ver}{ext}")

# DB에 모델 정보 삽입

def insert_model_record_to_db(learning_rate, model_name, epochs, batch_size):  # ✅ 인자 추가
    conn = pymysql.connect(
        host='localhost',
        user='root',
        password='1234',
        db='trip',
        charset='utf8mb4',
        autocommit=True
    )
    try:
        with conn.cursor() as cursor:
            cursor.execute("""
                UPDATE model
                SET is_active = 0
                WHERE MODEL_TYPE = 'MLP'
            """)

            sql = """
                INSERT INTO model (
                    LEARNINGRATE, CREATE_DATE,
                    INFORMATION, MODEL_TYPE, NAME, is_active,
                    EPOCHS, BATCH_SIZE  -- ✅ 새로운 필드 추가
                )
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            """
            cursor.execute(sql, (
                learning_rate,
                datetime.now().strftime('%Y-%m-%d %H:%M:%S'),
                'PyTorch MLP - satisfaction 예측',
                'MLP',
                model_name,
                1,
                epochs,         # ✅ 전달 값
                batch_size      # ✅ 전달 값
            ))
            print("[DB] 모델 정보 저장 및 is_active 설정 완료")
    finally:
        conn.close()

# 학습 메인

def train(config):
    user_path = '../data/user.csv'
    travel_path = '../data/travel.csv'
    visit_path = '../data/visit.csv'
    poi_path = '../data/poi.csv'
    data = get_preprocessed_data(user_path, travel_path, visit_path, poi_path)

    valid_travel_types = [str(i) for i in range(1, 13)]
    data = fixed_multi_hot_encode(data, 'travel_type', valid_travel_types)

    features = ['gender', 'age', 'season', 'means_tp', 'person', 'is_outdoor'] + \
               [f'travel_type_{tag}' for tag in valid_travel_types]

    data['is_outdoor'] = data['is_outdoor'].fillna(1)
    data[features] = data[features].fillna(-999)

    X = data[features].values.astype('float32')
    y = data['satisfaction'].astype('float32').values

    print(f"X.shape: {X.shape}")
    print(f"input_dim (특성 수): {X.shape[1]}")
    print("\n[디버그] X의 첫 5개 샘플:")
    print(X[:5])
    print("\n[디버그] 사용된 feature 목록:")
    print(features)

    X_tensor = torch.tensor(X, dtype=torch.float32)
    y_tensor = torch.tensor(y, dtype=torch.float32).view(-1, 1)

    dataset = TensorDataset(X_tensor, y_tensor)
    dataloader = DataLoader(dataset, batch_size=config.get("batch_size", 64), shuffle=True)

    model = MLP(input_dim=X.shape[1], hidden_dim=128, output_dim=1)
    criterion = nn.MSELoss()
    optimizer = optim.Adam(model.parameters(), lr=config.get("lr", 0.001))

    for epoch in range(config.get("epochs", 20)):
        model.train()
        total_loss = 0
        for xb, yb in dataloader:
            optimizer.zero_grad()
            preds = model(xb)
            loss = criterion(preds, yb)
            loss.backward()
            optimizer.step()
            total_loss += loss.item()
        print(f"[{epoch+1}/{config.get('epochs', 20)}] Loss: {total_loss/len(dataloader):.4f}")

    save_path = get_next_model_version("./model")
    torch.save(model.state_dict(), save_path)
    print(f"모델 학습 완료 및 저장됨: {save_path}")

    model_name = os.path.basename(save_path)
    insert_model_record_to_db(
        learning_rate=config.get("lr", 0.001),
        model_name=model_name,
        epochs=config.get("epochs", 20),         # ✅ 추가
        batch_size=config.get("batch_size", 64)  # ✅ 추가
    )

# 설정 로드

def load_config(config_path):
    if not os.path.exists(config_path):
        print(f"설정 파일이 존재하지 않음: {config_path}")
        return {}
    with open(config_path, 'r', encoding='utf-8') as f:
        config = json.load(f)
    print(f"설정 파일 로드 완료: {config}")
    return config

if __name__ == "__main__":
    config_path = sys.argv[1] if len(sys.argv) > 1 else None
    config = load_config(config_path) if config_path else {}
    train(config)