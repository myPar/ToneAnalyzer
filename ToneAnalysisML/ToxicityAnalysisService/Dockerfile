# Start from the official Python base image.
FROM python:3.11

# Set the current working directory to 'ml-service'
# This is where we'll put the requirements.txt file and the app directory.
WORKDIR /toxicity-analysis-service

#
COPY ./requirements.txt /toxicity-analysis-service/requirements.txt

# --no-cache-dir option tells pip to not save the downloaded packages locally in the cache
# because cache using is necessary when pip run again to install the same packages
# this is not about working with the containers
RUN pip install --no-cache-dir --upgrade -r /toxicity-analysis-service/requirements.txt

# copy
COPY ./entity_models /toxicity-analysis-service/entity_models
COPY ./ml_models /toxicity-analysis-service/ml_models
COPY ./services /toxicity-analysis-service/services
COPY main.py /toxicity-analysis-service/

#
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]