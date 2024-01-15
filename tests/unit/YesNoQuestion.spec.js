import YesNoQuestion from '@/models/YesNoQuestion'

/**
 * Test for the YesNoQuestion model class
 * @author Marco de Boer
 */

let yesnoQuestion

const yesnoQuestionJSON = {
  id: 17,
  question: 'Do you believe that providing affordable and clean energy is a key challenge for the Netherlands?',
  imgPath: 'http://localhost:8085/images/c00bcccb-9c49-47b0-b650-50076734a2e1.JPEG',
  sdg: 7,
  index: 3
}

beforeEach(() => {
  // Initialize the quiz object before each test
  yesnoQuestion = new YesNoQuestion(yesnoQuestionJSON.id, yesnoQuestionJSON.index,
    yesnoQuestionJSON.question, yesnoQuestionJSON.imgPath, yesnoQuestionJSON.sdg)
})

describe('Constructing a YesNoQuestion', () => {
  it('should create a YesNoQuestion object', () => {
    expect(yesnoQuestion, 'yesnoQuestion should be a YesNoQuestion Model').toBeInstanceOf(YesNoQuestion)
  })
  describe('should correctly set attributes', () => {
    it('should set the id', () => {
      expect(yesnoQuestion.id, 'id is not set correctly').toBe(yesnoQuestionJSON.id)
    })
    it('should set the index', () => {
      expect(yesnoQuestion.index, 'index is not set correctly').toBe(yesnoQuestionJSON.index)
    })
    it('should set the question', () => {
      expect(yesnoQuestion.question, 'question is not set correctly').toBe(yesnoQuestionJSON.question)
    })
    it('should set the imgPath', () => {
      expect(yesnoQuestion.imgPath, 'imgPath is not set correctly').toBe(yesnoQuestionJSON.imgPath)
    })
    it('should set the sdg', () => {
      expect(yesnoQuestion.sdg, 'sdg is not set correctly').toBe(yesnoQuestionJSON.sdg)
    })
    it('should set the givenAnswer to null', () => {
      expect(yesnoQuestion.givenAnswer, 'givenAnswer is not set correctly').toBeNull()
    })
  })
})

describe('Setting the givenAnswer', () => {
  it('with undefined should throw an error', () => {
    expect(() => {
      yesnoQuestion.setGivenAnswer(undefined)
    }).toThrow('answer is undefined')
  })
  it('with a string should throw an error', () => {
    expect(() => {
      yesnoQuestion.setGivenAnswer('answer')
    }).toThrow('answer is not a boolean')
  })
  it('with a number should throw an error', () => {
    expect(() => {
      yesnoQuestion.setGivenAnswer(1)
    }).toThrow('answer is not a boolean')
  })
  it('with a boolean should set the givenAnswer', () => {
    yesnoQuestion.setGivenAnswer(true)
    expect(yesnoQuestion.givenAnswer, 'givenAnswer is not set correctly').toBe(true)
  })
})

describe('Cloning a YesNoQuestion', () => {
  it('should clone the YesNoQuestion', async () => {
    const clone = await yesnoQuestion.clone()
    expect(clone, 'clone should be a YesNoQuestion Model').toBeInstanceOf(YesNoQuestion)
  })
  it('should have the same attributes', async () => {
    const clone = await yesnoQuestion.clone()
    expect(clone.id, 'id is not the same').toBe(yesnoQuestion.id)
    expect(clone.index, 'index is not the same').toBe(yesnoQuestion.index)
    expect(clone.question, 'question is not the same').toBe(yesnoQuestion.question)
    expect(clone.imgPath, 'imgPath is not the same').toBe(yesnoQuestion.imgPath)
    expect(clone.sdg, 'sdg is not the same').toBe(yesnoQuestion.sdg)
    expect(clone.givenAnswer, 'givenAnswer is not the same').toBe(yesnoQuestion.givenAnswer)
  })
})

describe('Comparing a YesNoQuestion', () => {
  it('with null should return false', () => {
    expect(yesnoQuestion.equals(null), 'comparing with null should return false').toBeFalsy()
  })
  it('with undefined should return false', () => {
    expect(yesnoQuestion.equals(undefined), 'comparing with undefined should return false').toBeFalsy()
  })
  it('with a string should return false', () => {
    expect(yesnoQuestion.equals('string'), 'comparing with a string should return false').toBeFalsy()
  })
  it('with a number should return false', () => {
    expect(yesnoQuestion.equals(1), 'comparing with a number should return false').toBeFalsy()
  })
  it('with a boolean should return false', () => {
    expect(yesnoQuestion.equals(true), 'comparing with a boolean should return false').toBeFalsy()
  })
  it('with the same YesNoQuestion should return true', () => {
    expect(yesnoQuestion.equals(yesnoQuestion), 'comparing with the same YesNoQuestion should return true').toBeTruthy()
  })
  it('with a different YesNoQuestion should return false', () => {
    const yesnoQuestion2 = new YesNoQuestion(2, 2, 'question', 'imgPath', 2)
    expect(yesnoQuestion.equals(yesnoQuestion2), 'comparing with a different YesNoQuestion should return false').toBeFalsy()
  })
})

describe('Using the copyConstructor', () => {
  it('with null should return null', () => {
    expect(YesNoQuestion.copyConstructor(null), 'copyConstructor with null should return null').toEqual(null)
  })
  it('with undefined should return null', () => {
    expect(YesNoQuestion.copyConstructor(undefined), 'copyConstructor with undefined should return null').toEqual(null)
  })
  it('with a string should return null', () => {
    expect(YesNoQuestion.copyConstructor('string'), 'copyConstructor with a string should return null').toEqual(null)
  })
  it('with a number should return null', () => {
    expect(YesNoQuestion.copyConstructor(1), 'copyConstructor with a number should return null').toEqual(null)
  })
  it('with a boolean should return null', () => {
    expect(YesNoQuestion.copyConstructor(true), 'copyConstructor with a boolean should return null').toEqual(null)
  })
  it('with a YesNoQuestion should return a copy', () => {
    const copy = YesNoQuestion.copyConstructor(yesnoQuestion)
    expect(copy, 'copyConstructor with a YesNoQuestion should return a copy').toBeInstanceOf(YesNoQuestion)
    expect(copy.id, 'id is not the same').toEqual(yesnoQuestion.id)
    expect(copy.index, 'index is not the same').toEqual(yesnoQuestion.index)
    expect(copy.question, 'question is not the same').toEqual(yesnoQuestion.question)
    expect(copy.imgPath, 'imgPath is not the same').toEqual(yesnoQuestion.imgPath)
    expect(copy.sdg, 'sdg is not the same').toEqual(yesnoQuestion.sdg)
    expect(copy.givenAnswer, 'givenAnswer is not the same').toEqual(yesnoQuestion.givenAnswer)
  })
})
