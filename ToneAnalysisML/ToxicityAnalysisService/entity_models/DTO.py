from pydantic import BaseModel, Field


class AnalysisRequestDTO(BaseModel):
    text: str = Field(min_length=1)


class AnalysisResponseDTO(BaseModel):
    analysis_mask: str = Field(pattern=r'(a|t)+', min_length=1)
    toxicity_ratio: float = Field(le=1, ge=0)
    text: str = Field(min_length=1)


