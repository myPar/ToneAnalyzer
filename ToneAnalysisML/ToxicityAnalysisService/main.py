from fastapi import FastAPI
from entity_models.DTO import AnalysisResponseDTO, AnalysisRequestDTO
from services.messages_analysis_service import analysis_service

app = FastAPI()


@app.post("/analysis")
async def analysis(dto: AnalysisRequestDTO) -> AnalysisResponseDTO:
    return analysis_service(dto)
