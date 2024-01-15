import Quiz from '@/models/Quiz.js'
import MultipleChoiceQuestion from '@/models/MultipleChoiceQuestion'
import YesNoQuestion from '@/models/YesNoQuestion'
import QuizResult from '@/models/QuizResult'
import Sector from '@/models/Sector'

/**
 * Test for the Quiz model class.
 * @author Marco de Boer
 */

const isInQuizBuilder = false

let quiz

beforeEach(() => {
  // Initialize the quiz object before each test
  quiz = new Quiz(quizJSON, isInQuizBuilder)
})

describe('Constructing a quiz outside quizbuilder', () => {
  it('with valid json should create a Quiz object', () => {
    const quiz2 = new Quiz(quizJSON, isInQuizBuilder)
    expect(quiz2, 'quiz should be a Quiz Model').toBeInstanceOf(Quiz)
  })
  it('with undefined json should throw an error', () => {
    expect(() => {
      // eslint-disable-next-line no-new
      new Quiz()
    }).toThrow('JSON is undefined')
  })
  it('with undefined name in json should throw an error', () => {
    const json = JSON.parse(JSON.stringify(quizJSON))
    json.name = undefined
    expect(() => {
      // eslint-disable-next-line no-new
      new Quiz(json, isInQuizBuilder)
    }).toThrow('name is undefined')
  })
  it('with undefined quizQuestions in json should throw an error', () => {
    const json = JSON.parse(JSON.stringify(quizJSON))
    json.quizQuestions = undefined
    expect(() => {
      // eslint-disable-next-line no-new
      new Quiz(json, isInQuizBuilder)
    }).toThrow('questions is undefined')
  })
  describe('Quiz instatiateQuiz method', () => {
    it('should create a multiplechoice question from quizQuestion[0]', () => {
      expect(quiz.quizQuestions[0], 'quizQuestions[0] should be a QuizQuestion Model').toBeInstanceOf(MultipleChoiceQuestion)
    })
    it('should create a yesno question from quizQuestion[1]', () => {
      expect(quiz.quizQuestions[1], 'quizQuestions[1] should be a QuizQuestion Model').toBeInstanceOf(YesNoQuestion)
    })
  })
  it('should instatiate Quiz results', () => {
    const quizResultObjectArray = []
    for (let i = 1; i < 18; i++) {
      quizResultObjectArray.push(new QuizResult(i, 0))
    }
    const quiz = new Quiz(quizJSON, isInQuizBuilder)
    expect(quiz.quizResultObjectArray, 'and should be an array of Quizresults').toEqual(quizResultObjectArray)
  })
})

describe('While the currentQuestionIndex', () => {
  describe('is 0', () => {
    it('the currentQuestion method should return the first question', () => {
      expect(quiz.getCurrentQuestion(), 'should return the first question').toEqual(quiz.quizQuestions[0])
    })
    it('the previousQuestion method should throw error', () => {
      quiz.currentQuestionIndex = 0
      expect(quiz.getPreviousQuestion(), 'should return null').toBeNull()
    })
    it('the nextQuestion method should return the second question', () => {
      quiz.currentQuestionIndex = 0
      expect(quiz.getNextQuestion(), 'should return the second question').toEqual(quiz.quizQuestions[1])
    })
  })
  describe('is 1', () => {
    it('the currentQuestion method should return the second question', () => {
      quiz.currentQuestionIndex = 1
      expect(quiz.getCurrentQuestion(), 'should return the first question').toEqual(quiz.quizQuestions[1])
    })
    it('the previousQuestion method should return the first question', () => {
      quiz.currentQuestionIndex = 1
      expect(quiz.getPreviousQuestion(), 'should return the first question').toEqual(quiz.quizQuestions[0])
    })
    it('the nextQuestion method should return null', () => {
      quiz.currentQuestionIndex = 1
      expect(quiz.getNextQuestion(), 'should return null').toBeNull()
    })
  })
})

describe('When answering a question the totalAnsweredQuestions should be increased by 1', () => {
  it('should be 0 when no question is answered', () => {
    expect(quiz.getTotalAnsweredQuestions(), 'should be 0').toEqual(0)
  })
  it('should be 1 when multiplechoicequestion is answered', () => {
    quiz.quizQuestions[0].options[0].isSelected = true
    quiz.quizQuestions[0].setGivenAnswers()
    expect(quiz.getTotalAnsweredQuestions(), 'should be 1').toEqual(1)
  })
  it('should be 1 when yesnoquestion is answered', () => {
    quiz.quizQuestions[1].givenAnswer = true
    expect(quiz.getTotalAnsweredQuestions(), 'should be 1').toEqual(1)
  })
})

describe('When cloning a quiz', () => {
  it('should return a new quiz with the same values', () => {
    const clonedQuiz = quiz.clone()
    expect(clonedQuiz, 'should be a Quiz Model').toBeInstanceOf(Quiz)
    expect(clonedQuiz.quizQuestions[0], 'should be a QuizQuestion Model').toBeInstanceOf(MultipleChoiceQuestion)
    expect(clonedQuiz.quizQuestions[1], 'should be a QuizQuestion Model').toBeInstanceOf(YesNoQuestion)
    expect(clonedQuiz.quizResultObjectArray, 'and should be an array of Quizresults').toEqual(quiz.quizResultObjectArray)
  })
  it('should equal itself', function () {
    const quiz3 = new Quiz(quizJSON, isInQuizBuilder)
    const clonedQuiz = quiz3.clone()
    expect(clonedQuiz).toEqual(quiz3)
  })
})

describe('When using the equals method', () => {
  it('should return true when comparing a quiz with itself', () => {
    expect(quiz.equals(quiz)).toEqual(true)
  })
  it('should return false when comparing a quiz with null', () => {
    expect(quiz.equals(null)).toEqual(false)
  })
  it('should return false when comparing a quiz with undefined', () => {
    expect(quiz.equals(undefined)).toEqual(false)
  })
  it('should return false when comparing a quiz with a quiz with different name', () => {
    const quiz2 = new Quiz(quizJSON, isInQuizBuilder)
    quiz2.name = 'test'
    expect(quiz.equals(quiz2)).toEqual(false)
  })
  it('should return false when comparing a quiz with a quiz with different quizQuestions', () => {
    const quiz2 = new Quiz(quizJSON, isInQuizBuilder)
    quiz2.quizQuestions[0].question = 'test'
    expect(quiz.equals(quiz2)).toEqual(false)
  })
  it('should return false when comparing a quiz with a quiz with different sector', () => {
    const quiz2 = new Quiz(quizJSON, isInQuizBuilder)
    const sector2 = new Sector({
      id: 2,
      name: 'None',
      description: 'No sector assigned'
    })
    quiz2.sector = sector2
    expect(quiz.equals(quiz2)).toEqual(false)
  })
})

describe('When using the copyConstructor method', () => {
  it('should return null when using null', () => {
    expect(Quiz.copyConstructor(null)).toEqual(null)
  })
  it('should return null when using undefined', () => {
    expect(Quiz.copyConstructor(undefined)).toEqual(null)
  })
  it('should return a quiz when using a valid json', () => {
    expect(quiz, 'quiz should be a Quiz Model').toBeInstanceOf(Quiz)
  })
})

const quizJSON = {
  id: 1,
  name: 'Starter',
  isPublished: true,
  isConcept: false,
  isLive: true,
  sector: {
    id: 1,
    name: 'None',
    description: 'No sector assigned'
  },
  quizQuestions: [
    {
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
    },
    {
      id: 17,
      question: 'Do you believe that providing affordable and clean energy is a key challenge for the Netherlands?',
      imgPath: 'http://localhost:8085/images/c00bcccb-9c49-47b0-b650-50076734a2e1.JPEG',
      sdg: 7,
      index: 3,
      type: 'yesno'
    }
  ]
}
