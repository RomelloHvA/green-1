package app.rest;

import app.models.MultipleChoiceOption;
import app.services.MultipleChoiceOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/multiplechoiceoption")
public class MultipleChoiceOptionController {

    private final MultipleChoiceOptionService multipleChoiceOptionService;

    @Autowired
    public MultipleChoiceOptionController(MultipleChoiceOptionService multipleChoiceOptionService) {
        this.multipleChoiceOptionService = multipleChoiceOptionService;
    }

    @GetMapping
    public List<MultipleChoiceOption> getMultipleChoiceOptions() {
        return multipleChoiceOptionService.getMultipleChoiceOptions();
    }

    @GetMapping("/{id}")
    public MultipleChoiceOption getMultipleChoiceOptionById(Long id) {
        return multipleChoiceOptionService.getMultipleChoiceOptionById(id);
    }

    @PostMapping("/create/{questionindex}")
    public MultipleChoiceOption addMultipleChoiceOption(@PathVariable Long questionindex ,MultipleChoiceOption multipleChoiceOption) {
        return multipleChoiceOptionService.addMultipleChoiceOption(questionindex ,multipleChoiceOption);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteMultipleChoiceOption(Long id) {
        return multipleChoiceOptionService.deleteMultipleChoiceOption(id);
    }

}
