import QuizResult from '@/models/QuizResult'

describe('QuizResult Model', () => {
  it('should create a valid QuizResult instance with default score', () => {
    const quizResult = new QuizResult(3)

    expect(quizResult).toBeInstanceOf(QuizResult)
    expect(quizResult.SDG).toBe(3)
    expect(quizResult.score).toBe(0)
  })

  it('should create a valid QuizResult instance with a specified score', () => {
    const quizResult = new QuizResult(7, 80)// Assuming SDG 7 and a score of 80 for testing

    expect(quizResult).toBeInstanceOf(QuizResult)
    expect(quizResult.SDG).toBe(7)
    expect(quizResult.score).toBe(80)
  })
})
describe('QuizResult Model error', () => {
  it('should throw an error if SDG or score is undefined', () => {
    expect(() => new QuizResult()).toThrowError('SDG or score is undefined')
  })

  it('should throw an error if SDG is not between 1 and 17', () => {
    expect(() => new QuizResult(18)).toThrowError('SDG is not between 1 and 17')
  })
  it('should throw an error if score is not a number', () => {
    expect(() => new QuizResult(5, 'invalid')).toThrowError('score is not a number')
  })

  it('should throw an error if SDG is not a number', () => {
    expect(() => new QuizResult('invalid', 75)).toThrowError('score is not a number')
  })
})
