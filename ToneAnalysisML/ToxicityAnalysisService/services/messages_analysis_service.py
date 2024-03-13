from entity_models.DTO import *
from services.model_config import model_wrapper, TOXIC
import re


def analysis_service(requestDTO: AnalysisRequestDTO) -> AnalysisResponseDTO:
    text = requestDTO.text

    drop_empty = lambda x: len(x) > 0
    pattern = "[.!?)(]+"
    tokens = list(filter(drop_empty, re.split(pattern, text)))

    analysis_mask = ""
    toxicity_ratio = 0.0

    for token in tokens:
        label_text, _ = model_wrapper.predict_probs(token.strip())
        label_char = label_text[0].lower()  # 't'/'a' will be produced
        analysis_mask += label_char

        if label_text == TOXIC:
            toxicity_ratio += 1

    toxicity_ratio /= len(tokens)

    return AnalysisResponseDTO(toxicity_ratio=toxicity_ratio,
                               analysis_mask=analysis_mask,
                               text=text)
