package app.services;

import app.models.MultipleChoiceOption;
import app.models.MultipleChoiceQuestion;
import app.models.Question;
import app.repositories.MultipleChoiceOptionRepository;
import app.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultipleChoiceOptionService {

    private final MultipleChoiceOptionRepository multipleChoiceOptionRepository;

    private final QuestionService questionService;

    @Autowired
    public MultipleChoiceOptionService(MultipleChoiceOptionRepository multipleChoiceOptionRepository, QuestionService questionService) {
        this.multipleChoiceOptionRepository = multipleChoiceOptionRepository;
        this.questionService = questionService;
    }

    public List<MultipleChoiceOption> getMultipleChoiceOptions() {
        return multipleChoiceOptionRepository.findAll();
    }

    public MultipleChoiceOption getMultipleChoiceOptionById(Long id) {
        return multipleChoiceOptionRepository.findById(id).orElse(null);
    }

    public MultipleChoiceOption addMultipleChoiceOption(Long questionindex,MultipleChoiceOption multipleChoiceOption) {
        Question question = questionService.getQuestionById(questionindex);
        if(question == null || !(question instanceof MultipleChoiceQuestion)){
            throw new RuntimeException("Question not found");
        }
        multipleChoiceOption.setMultipleChoiceQuestion((MultipleChoiceQuestion) question);
        multipleChoiceOptionRepository.save(multipleChoiceOption);
        return multipleChoiceOption;
    }

    public boolean deleteMultipleChoiceOption(Long id) {
        MultipleChoiceOption multipleChoiceOption = multipleChoiceOptionRepository.findById(id).orElse(null);
        if (multipleChoiceOption == null) {
            return false;
        }
        multipleChoiceOptionRepository.delete(multipleChoiceOption);
        return true;
    }
}
