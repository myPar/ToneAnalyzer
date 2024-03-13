from entity_models.DTO import TextAnalysisRequestDTO, TextAnalysisResponseDTO
import regex as re
from services.model_config import ml_model

label_dict = dict({"NEUTRAL": "i",
                   "POSITIVE": "p",
                   "NEGATIVE": "n"
                   })


def tone_analysis(requestDTO: TextAnalysisRequestDTO):
    text: str = requestDTO.text

    drop_empty = lambda x: len(x) > 0
    pattern = "[.!?)(]+"
    tokens = list(filter(drop_empty, re.split(pattern, text)))
    analysis_mask = ""

    for token in tokens:
        conclusion, _ = ml_model.predict_probs(token.strip())
        label = label_dict.get(conclusion)
        analysis_mask += label

    return TextAnalysisResponseDTO(analysis_mask=analysis_mask, text=text)
