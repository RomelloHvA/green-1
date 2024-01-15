import CONFIG from '../../app-config'
import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import Quiz from '@/models/Quiz'
import { enableFetchMocks } from 'jest-fetch-mock'
enableFetchMocks()

const quizService = new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/quiz', Quiz.copyConstructor)

beforeEach(() => {
  fetch.resetMocks()
})

/**
 * Test quizService
 * @author Marco de Boer
 */
describe('Method asyncFindAll', () => {
  it('should return all quizzes', async function () {
    fetch.mockResponseOnce(JSON.stringify([mockedResponseJSONData, mockedResponseJSONData]))
    const result = await quizService.asyncFindAll()
    await result.load()
    expect(result.error.value, 'error has happend').toBeNull()
    expect(result.entities.value.length, 'not the expected amount of quizzes returned').toBe(2)
    expect(result.entities.value[0], 'returned entities are not instances of Quiz').toBeInstanceOf(Quiz)
  })
  it('should return [] if there was nothing', async function () {
    fetch.mockResponseOnce(JSON.stringify([]))
    const result = await quizService.asyncFindAll()
    await result.load()
    expect(result.error.value).toBeNull()
    expect(result.entities.value).toStrictEqual([])
  })
})

describe('Method asyncFindById', () => {
  it('should return null if id is not found', async function () {
    fetch.mockResponseOnce(JSON.stringify(null))
    const result = await quizService.asyncFindById(2)
    await result.load()
    expect(result.error.value).toBeNull()
    expect(result.entity.value).toBeNull()
  })
  it('should return quiz with id 1', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockedResponseJSONData))
    const result = await quizService.asyncFindById(1)
    await result.load()
    expect(result.error.value).toBeNull()
    expect(result.entity.value.id).toBe(1)
    expect(result.entity.value, 'returned entity is not an instances of Quiz').toBeInstanceOf(Quiz)
  })
  it('should return a error if something is incorrect', async function () {
    fetch.mockResponseOnce(null, { status: 404 })
    const result = await quizService.asyncFindById(2000)
    await result.load()
    expect(result.error.value).not.toBeNull()
  })
})

describe('Method asyncSave', () => {
  it('should save a quiz', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockedResponseJSONData))
    const result = await quizService.asyncSave(mockedResponseJSONData)
    await result.load()
    expect(result.error.value).toBeNull()
    expect(result.entity.value, 'returned entity is not an instances of Quiz').toBeInstanceOf(Quiz)
  })
  it('should update a quiz', async function () {
    mockedResponseJSONData.id = 2
    fetch.mockResponseOnce(JSON.stringify(mockedResponseJSONData))
    const result = await quizService.asyncSave(mockedResponseJSONData)
    await result.load()
    expect(result.error.value).toBeNull()
    expect(result.entity.value, 'returned entity is not an instances of Quiz').toBeInstanceOf(Quiz)
  })
  it('should return a error if something is incorrect', async function () {
    fetch.mockResponseOnce(null, { status: 404 })
    const result = await quizService.asyncSave(2000)
    await result.load()
    expect(result.error.value).not.toBeNull()
  })
})

describe('Method asyncDelete', () => {
  it('should delete a quiz', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockedResponseJSONData))
    const result = await quizService.asyncDeleteById(1)
    await result.load()
    expect(result.error.value).toBeNull()
  })
  it('should return a error if something is incorrect', async function () {
    fetch.mockResponseOnce(null, { status: 404 })
    const result = await quizService.asyncDeleteById(null)
    await result.load()
    expect(result.error.value).not.toBeNull()
  })
})

describe('Method asyncCustom', () => {
  it('should return a live quiz', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockedResponseJSONData))
    const result = await quizService.asyncCustom('live', 'GET')
    await result.load()
    expect(result.error.value).toBeNull()
    expect(result.entity.value, 'returned entity is not an instances of Quiz').toBeInstanceOf(Quiz)
    expect(result.entity.value.isLive).toBe(true)
  })
  it('should return a error if endpoint is does not exist', async function () {
    fetch.mockResponseOnce(null, { status: 404 })
    const result = await quizService.asyncCustom()
    await result.load()
    expect(result.error.value).not.toBeNull()
    expect(result.entity.value).toStrictEqual([])
  })
})

const mockedResponseJSONData = {
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
      type: 'multiplechoice',
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
      index: 1
    },
    {
      id: 15,
      question: 'What aspect of environmental conservation in the Netherlands do you find most compelling',
      imgPath: 'http://localhost:8085/images/08475eb8-aa5d-4245-a506-fb37007acbc0.JPEG',
      options: [
        {
          id: 18,
          option: 'Reducing carbon emissions',
          sdg: 13
        },
        {
          id: 19,
          option: 'Promoting sustainable agriculture',
          sdg: 2
        },
        {
          id: 20,
          option: 'awefaw',
          sdg: 15
        }
      ],
      answerLimit: 1,
      index: 2,
      type: 'multiplechoice'
    },
    {
      id: 17,
      question: 'Do you believe that providing affordable and clean energy is a key challenge for the Netherlands?',
      imgPath: 'http://localhost:8085/images/c00bcccb-9c49-47b0-b650-50076734a2e1.JPEG',
      sdg: 7,
      index: 3,
      type: 'yesno'
    },
    {
      id: 24,
      question: "Do you consider innovation and infrastructure development key to the Netherlands' future success?",
      imgPath: 'http://localhost:8085/images/f1b58d4d-2b9e-4332-84d0-8eee147c47cc.JPEG',
      sdg: 9,
      index: 4,
      type: 'yesno'
    },
    {
      id: 37,
      question: ' In which areas do you think the Netherlands should focus its efforts to promote sustainable development?',
      imgPath: 'http://localhost:8085/images/5ff46013-f4d4-48f3-bba2-0791f38f5653.jpg',
      options: [
        {
          id: 49,
          option: 'Improving public transportation',
          sdg: 11
        },
        {
          id: 50,
          option: 'Promoting renewable energy sources',
          sdg: 7
        },
        {
          id: 51,
          option: 'Enhancing waste recycling programs',
          sdg: 12
        },
        {
          id: 52,
          option: 'Conserving coastal and marine areas',
          sdg: 9
        }
      ],
      answerLimit: 1,
      index: 5,
      type: 'multiplechoice'
    },
    {
      id: 39,
      question: 'Which of these global challenges do you think is most relevant to address in the Netherlands?',
      imgPath: null,
      options: [
        {
          id: 57,
          option: 'Gender equality in STEM fields',
          sdg: 5
        },
        {
          id: 58,
          option: 'Economic growth and job creation',
          sdg: 8
        },
        {
          id: 59,
          option: 'Mental health and well-being',
          sdg: 3
        },
        {
          id: 60,
          option: 'Sustainable agriculture and food systems',
          sdg: 2
        }
      ],
      answerLimit: 3,
      index: 6,
      type: 'multiplechoice'
    },
    {
      id: 41,
      question: 'What kind of local community projects would you be interested in participating in?',
      imgPath: null,
      options: [
        {
          id: 65,
          option: 'Community-driven clean energy initiatives"',
          sdg: 7
        },
        {
          id: 66,
          option: 'Programs for inclusive and equitable education',
          sdg: 4
        },
        {
          id: 67,
          option: 'Local campaigns for reducing plastic use',
          sdg: 12
        },
        {
          id: 68,
          option: 'Urban gardening and green space projects',
          sdg: 15
        }
      ],
      answerLimit: 4,
      index: 7,
      type: 'multiplechoice'
    },
    {
      id: 43,
      question: 'Which aspects of climate action do you find most pressing for the Netherlands?',
      imgPath: null,
      options: [
        {
          id: 73,
          option: 'Sea level rise and coastal protection',
          sdg: 13
        },
        {
          id: 74,
          option: 'Renewable energy adoption',
          sdg: 7
        },
        {
          id: 75,
          option: 'Sustainable transportation',
          sdg: 11
        },
        {
          id: 76,
          option: 'Carbon footprint reduction in industries',
          sdg: 9
        }
      ],
      answerLimit: 2,
      index: 8,
      type: 'multiplechoice'
    },
    {
      id: 45,
      question: 'Do you believe that improving air quality in urban areas is a critical issue for the Netherlands?',
      imgPath: null,
      sdg: 11,
      index: 9,
      type: 'yesno'
    },
    {
      id: 46,
      question: 'Which of these issues do you think is most pressing for the youth in the Netherlands?',
      imgPath: null,
      options: [
        {
          id: 77,
          option: 'Mental health awareness',
          sdg: 3
        },
        {
          id: 78,
          option: 'Access to quality education',
          sdg: 4
        },
        {
          id: 79,
          option: 'Employment opportunities',
          sdg: 8
        },
        {
          id: 80,
          option: 'Climate change activism',
          sdg: 13
        }
      ],
      answerLimit: 1,
      index: 10,
      type: 'multiplechoice'
    }
  ]
}
