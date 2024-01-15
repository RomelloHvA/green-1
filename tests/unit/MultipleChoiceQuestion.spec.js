/**
 * Test for the YesNoQuestion model class
 * @author Marco de Boer
 */
import MultipleChoiceQuestion from '@/models/MultipleChoiceQuestion'
import MultipleChoiceOption from '@/models/MultipleChoiceOption'

let multipleChoiceQuestion

beforeEach(() => {
  // Initialize the quiz object before each test
  multipleChoiceQuestion = new MultipleChoiceQuestion(multipleChoiceQuestionJSON.id, multipleChoiceQuestionJSON.index,
    multipleChoiceQuestionJSON.question, multipleChoiceQuestionJSON.imgPath, multipleChoiceQuestionJSON.options,
    multipleChoiceQuestionJSON.answerLimit)
})

describe('Constructing a MultipleChoiceQuestion', () => {
  it('should create a MultipleChoiceQuestion object', () => {
    expect(multipleChoiceQuestion, 'multipleChoiceQuestion should be a MultipleChoiceQuestion Model').toBeInstanceOf(MultipleChoiceQuestion)
  })
  describe('should correctly set attributes', () => {
    it('should set the id', () => {
      expect(multipleChoiceQuestion.id, 'id is not set correctly').toBe(multipleChoiceQuestionJSON.id)
    })
    it('should set the index', () => {
      expect(multipleChoiceQuestion.index, 'index is not set correctly').toBe(multipleChoiceQuestionJSON.index)
    })
    it('should set the question', () => {
      expect(multipleChoiceQuestion.question, 'question is not set correctly').toBe(multipleChoiceQuestionJSON.question)
    })
    it('should set the imgPath', () => {
      expect(multipleChoiceQuestion.imgPath, 'imgPath is not set correctly').toBe(multipleChoiceQuestionJSON.imgPath)
    })
    it('should set the options', () => {
      expect(multipleChoiceQuestion.options[0], 'options is not set correctly as multiplechoiceoption object').toBeInstanceOf(MultipleChoiceOption)
    })
    it('should set the answerLimit', () => {
      expect(multipleChoiceQuestion.answerLimit, 'answerLimit is not set correctly').toBe(multipleChoiceQuestionJSON.answerLimit)
    })
    it('should set the givenAnswers to an empty array', () => {
      expect(multipleChoiceQuestion.givenAnswers, 'givenAnswers is not set correctly').toEqual([])
    })
  })
})

describe('Setting the givenAnswers', () => {
  it('should set index 1 true for options 2 selected', () => {
    multipleChoiceQuestion.options[1].isSelected = true
    multipleChoiceQuestion.setGivenAnswers()
    expect(multipleChoiceQuestion.givenAnswers[1], 'givenAnswers[1] is not set correctly').toBeTruthy()
  })
  it('should set index 1 false for options 2 not selected', () => {
    multipleChoiceQuestion.options[1].isSelected = false
    multipleChoiceQuestion.setGivenAnswers()
    expect(multipleChoiceQuestion.givenAnswers[1], 'givenAnswers[1] is not set correctly').toBeFalsy()
  })
  it('should result in correct givenAnwerAmount', async () => {
    multipleChoiceQuestion.options[1].isSelected = true
    multipleChoiceQuestion.options[2].isSelected = true
    multipleChoiceQuestion.setGivenAnswers()
    expect(await multipleChoiceQuestion.getSelectedAnswersAmount(), 'givenAnswersAmount is not correct').toBe(2)
  })
})

describe('Cloning a MultipleChoiceQuestion', () => {
  it('should clone the MultipleChoiceQuestion', async () => {
    const clone = await multipleChoiceQuestion.clone()
    expect(clone, 'clone should be a MultipleChoiceQuestion Model').toBeInstanceOf(MultipleChoiceQuestion)
  })
  it('should have the same values as the original', async () => {
    const clone = await multipleChoiceQuestion.clone()
    expect(clone, 'clone should be the same as the original').toEqual(multipleChoiceQuestion)
  })
})

describe('Comparing a MultipleChoiceQuestion', () => {
  it('with null should return false', () => {
    expect(multipleChoiceQuestion.equals(null), 'comparing with null should return false').toBeFalsy()
  })
  it('with undefined should return false', () => {
    expect(multipleChoiceQuestion.equals(undefined), 'comparing with undefined should return false').toBeFalsy()
  })
  it('with a string should return false', () => {
    expect(multipleChoiceQuestion.equals('string'), 'comparing with a string should return false').toBeFalsy()
  })
  it('with a number should return false', () => {
    expect(multipleChoiceQuestion.equals(1), 'comparing with a number should return false').toBeFalsy()
  })
  it('with a boolean should return false', () => {
    expect(multipleChoiceQuestion.equals(true), 'comparing with a boolean should return false').toBeFalsy()
  })
  it('with the same MultipleChoiceQuestion should return true', () => {
    expect(multipleChoiceQuestion.equals(multipleChoiceQuestion), 'comparing with the same MultipleChoiceQuestion should return true').toBeTruthy()
  })
  it('with a different MultipleChoiceQuestion should return false', () => {
    const multipleChoiceQuestion2 = new MultipleChoiceQuestion(2, 2, 'question', 'imgPath', [])
    expect(multipleChoiceQuestion.equals(multipleChoiceQuestion2), 'comparing with a different MultipleChoiceQuestion should return false').toBeFalsy()
  })
})

const multipleChoiceQuestionJSON = {
  id: 21,
  question: 'Which social issue do you think is most pressing in the Netherlands?',
  imgPath: 'http://localhost:8085/images/889682f5-dade-4d3d-8167-2071744ad76b.PNG',
  options: [
    {
      id: 25,
      option: 'Gender equality in the workforce',
      sdg: 5
    },
    {
      id: 26,
      option: 'Quality education for all',
      sdg: 4
    },
    {
      id: 27,
      option: 'Reducing income inequality',
      sdg: 10
    },
    {
      id: 28,
      option: 'Ensuring healthy lives for all age groups',
      sdg: 3
    }
  ],
  answerLimit: 1,
  index: 1,
  type: 'multiplechoice'
}
