FROM python:3.6-slim@sha256:2cfebc27956e6a55f78606864d91fe527696f9e32a724e6f9702b5f9602d0474

RUN mkdir -p /opt/calc

WORKDIR /opt/calc

COPY .coveragerc .pylintrc pyproject.toml pytest.ini requires ./
COPY app ./app
COPY test ./test
RUN pip install -r requires
