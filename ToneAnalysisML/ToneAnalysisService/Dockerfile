# Start from the official Python base image.
FROM python:3.11
# set main dir
WORKDIR /tone-analysis-service
# copy requirements
COPY ./requirements.txt /tone-analysis-service/requirements.txt

# install requirements
RUN pip install --no-cache-dir --upgrade -r /tone-analysis-service/requirements.txt

# copy sources and model
COPY ./entity_models /tone-analysis-service/entity_models
COPY ./services /tone-analysis-service/services
COPY ./ml_models /tone-analysis-service/ml_models
COPY ./main.py /tone-analysis-service/

# start app command
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8080"]