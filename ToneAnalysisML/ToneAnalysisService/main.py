from fastapi import FastAPI

from entity_models.DTO import TextAnalysisRequestDTO, TextAnalysisResponseDTO
from services.tone_analysis_service import tone_analysis

app = FastAPI()


@app.post("/analysis")
async def analysis(analysis_dto: TextAnalysisRequestDTO) -> TextAnalysisResponseDTO:
    return tone_analysis(analysis_dto)
