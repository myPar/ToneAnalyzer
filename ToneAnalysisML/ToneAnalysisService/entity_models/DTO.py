from pydantic import BaseModel, Field, model_validator


class TextAnalysisRequestDTO(BaseModel):
    text: str = Field(min_length=1)


class TextAnalysisResponseDTO(BaseModel):
    text: str = Field(min_length=1)
    analysis_mask: str
