import { mount } from '@vue/test-utils'
import { RESTQuizResultAdaptor } from '@/services/RESTQuizResultAdaptor'
import CONFIG from '../../app-config'
import ResultPage from '@/components/result/ResultPage'
import router from '@/router'

/**
 * Test for ResultPage
 * @author Santosh Kakkar
 */
const quizResultService = new RESTQuizResultAdaptor(CONFIG.BACKEND_URL + '/quizresult')

let wrapper

beforeEach(() => {
  fetch.resetMocks()
  const mockUsersServices = {
    asyncSave: jest.fn()
  }
  wrapper = mount(ResultPage, {
    // Providing mockUserService as injection
    global: {
      plugins: [router],
      provide: {
        usersServices: mockUsersServices
      }
    }
  })
})

describe('Getting results with async getUserById', () => {
  it('should return quizResults of user', async function () {
    // Feches a mock response with null
    fetch.mockResponseOnce(JSON.stringify(mockQuizResults), { headers: { 'Content-Type': 'application/json' }, method: 'GET' })
    const response = await quizResultService.getByUserId(mockUser.user_id)
    expect(response, 'There are no results for this user').not.toBeNull()
  })
})

describe('Sort buttons exists', () => {
  it('Should have sorting button', () => {
    expect(wrapper.find('#sortButton'), 'There is no sorting button').toBeTruthy()
  })
  it('Should have ascending button', () => {
    expect(wrapper.find('#top'), 'There is no ascending button found ').toBeTruthy()
  })
  it('Should have descending button', () => {
    expect(wrapper.find('#bottom'), 'There is no descending button found ').toBeTruthy()
  })
})

describe('Sort buttons', () => {
  it('Should sort results in ascending order when "Date ascending" button is clicked', async () => {
    expect(wrapper.vm.sortDirection).toBe('desc')

    // Click the "Date ascending" button
    await wrapper.find('#top').trigger('click')

    expect(wrapper.vm.sortDirection).toBe('asc')
  })

  it('Should sort results in descending order when "Date descending" button is clicked', async () => {
    expect(wrapper.vm.sortDirection).toBe('asc')

    // Click the "Date descending" button
    await wrapper.find('#bottom').trigger('click')

    expect(wrapper.vm.sortDirection).toBe('asc')
  })
})

const mockUser = {
  user_id: 3,
  sector_id: 1,
  first_name: 'User27',
  last_name: 'User27',
  email: 'User27@gmail.com',
  security_clearance: 1,
  password: '1234',
  username: 'User',
  bio: 'leeg',
  occupation: 'idk',
  date_of_birth: '2000-02-01',
  postalcode: null,
  user_goal: null,
  actionplans: [
    {
      id: 3,
      title: 'Enhance Global Education Awareness',
      description: '  <strong>Objective:</strong> To increase awareness and support for SDG 4, Quality Education, among a broad audience.<br><strong>Actions:</strong><br>* Research and share information about global education challenges and successes.<br>* Organize or participate in educational events or webinars focusing on SDG 4.<br>* Volunteer with organizations that focus on improving education quality worldwide.<br>* Advocate for educational policies that align with SDG 4 objectives.<br>* Collaborate with educational institutions to develop SDG 4-focused programs.\n',
      sector: {
        id: 1,
        name: 'None',
        description: 'No sector assigned'
      },
      sdgArray: [
        4,
        5,
        7,
        10
      ]
    }
  ],
  img_path: 'http://localhost:8085/images/a14bb9ef-f45e-4229-9af3-81a17aa503a7.PNG',
  isAdmin: true
}

const mockQuizResults = [
  {
    resultId: 102,
    sdgArray: [
      2,
      3,
      15
    ],
    dateOfQuiz: [
      2024,
      1,
      6
    ]
  },
  {
    resultId: 152,
    sdgArray: [
      2,
      3,
      15
    ],
    dateOfQuiz: [
      2024,
      1,
      6
    ]
  }
]
