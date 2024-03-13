### Как использовать

#### 1. Устанавливаем Docker

[ссылка на официальный сайт](https://www.docker.com/)

#### 2. Клонируем репозиторий

```bash
cd  your_dir
git clone https://github.com/NSU-CSD/ml-service.git
```

#### 3. Копируем конфигурацию модели

Копируем [папку с конфигурацией модели](https://drive.google.com/drive/folders/1F3gDehcZXyVgHNvmJ858wyfKXzk4xxUA?usp=sharing) в директорию с проектом (это директория в которой расположены файлы __Dockerfile__ и __requirements.txt__)

Папка должна называться __ml_models__ и содержать подпапки __model__ и __tokenizer__

#### 4. Строим контейнер

Нужно перейти в папку с проектом. Затем в терминале набираем следующие команды:

построение контейнера:
```bash
docker build -t krpo_ml_service .
```

запуск:

```bash
docker run -d --name ml_service -p 8000:80 krpo_ml_service
```

#### 5. Запросы к сервису

у сервиса один енд-пойнт - это POST запрос:

url: http://0.0.0.0:8000/analysis
body:
```json
{
"messages":
    [
        {
            "user_id": "@tg_trdn",
            "message": "всем привет",
            "publication_time": "02.05.2022 19:45" 
        },
        ...
    ]
}
```

response:

```json
{
    "messages": 
    [
        {
            "user_id": "@tg_trdn",
            "message": "всем привет",
            "publication_time": "02.05.2022 19:45",
            "analysis_mask": "a",
            "toxicity_ratio": 0.0
        },
        ...
    ]
}
```