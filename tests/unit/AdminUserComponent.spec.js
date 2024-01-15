/**
 * Test for the AdminUser component
 * @author Santosh Kakkar
 */

import { mount } from '@vue/test-utils'
import AdminUserComponent from '@/components/AdminDashboard/AdminUserComponent'
import { UsersAdaptor } from '@/services/users-adaptor'
import CONFIG from '../../app-config'
import { enableFetchMocks } from 'jest-fetch-mock'

const usersServices = new UsersAdaptor(CONFIG.BACKEND_URL)
enableFetchMocks()
let wrapper

beforeEach(async function () {
  const mockUsersServices = {
    asyncFindAll: jest.fn().mockResolvedValue(mockedUsers),
    asyncSave: jest.fn()
  }

  wrapper = mount(AdminUserComponent, {
    // Providing mockUserService as injection
    global: {
      provide: {
        usersServices: mockUsersServices
      }
    },
    data () {
      return {
        users: mockedUsers,
        searchUser: ''
      }
    }
  })
})

describe('AdminUserComponent', () => {
  it('correctly shown with default values', async () => {
    // Check if the component loads correctly
    expect(wrapper.exists()).toBe(true)

    // Check if default data values are set
    expect(wrapper.vm.users).toEqual(mockedUsers)
    expect(wrapper.vm.searchUser).toBe('')

    // You can also test the presence of certain elements if needed
    expect(wrapper.find('#searchbar').exists()).toBe(true)
    expect(wrapper.find('.table').exists()).toBe(true)

    // Check if the component fetches users on creation (async)
    await wrapper.vm.$nextTick()
    expect(wrapper.vm.users).toEqual(mockedUsers)
  })
  it('updates the searchUser data when input value changes', async () => {
    // Set the input value
    await wrapper.setData({ searchUser: 'Gijs' })

    // Check if the searchUser data is updated
    expect(wrapper.vm.searchUser).toBe('Gijs')
  })
  it('toggles the EditPopUp when clicking the Edit button', async () => {
    // Wait for the asynchronous created hook to complete
    await wrapper.vm.$nextTick()

    // Button is made if users_id is present
    const editButton = wrapper.findAll('#editButton')
    expect(editButton.at(1),
      'Cannot find button with id = editButton').toBeTruthy()
    // Choose button with the user_id 1
    const firstEditButton = editButton.at(1)
    await firstEditButton.trigger('click')
    // Check if the EditPopUp is toggled
    expect(wrapper.vm.popUpTrigger.isEditing).toBe(true)
  })
})

/**
 * Test for the Admin User Component
 * @Author Justin Chan
 */
describe('Load all users from the database', () => {
  it('it should have a list of users', async () => {
    fetch.mockResponseOnce(JSON.stringify([mockUserData1, mockUserData2]))
    const users = await usersServices.asyncFindAll()

    expect(users.length).toBeGreaterThan(0)
  })
})
describe('Saving/updating a account', () => {
  it('should be able to make changes to account', async () => {
    const user = JSON.parse(JSON.stringify(mockUserData2))
    user.isAdmin = true
    user.username = 'marcus'
    user.email = 'kart@hva.nl'
    fetch.mockResponseOnce(JSON.stringify(user), {
      status: 200,
      statusText: 'OK',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'PUT'
    })
    const response = await usersServices.asyncSave(user)
    expect(response.username).toBe('marcus')
    expect(response.email).toBe('kart@hva.nl')
    expect(response.isAdmin).toBe(true)
  })
})

/**
 * Test for the Admin User Component
 * @Author Justin Chan
 */
describe('Load all users from the database', () => {
  it('it should have a list of users', async () => {
    fetch.mockResponseOnce(JSON.stringify(mockUsers), {
      status: 200,
      statusText: 'OK',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'GET'
    })
    const users = await usersServices.asyncFindAll()

    expect(users, 'users should exist').toBeTruthy()
    expect(users.length, 'users should have a length of 2').toBe(2)
  })
})
describe('Saving/updating a account', () => {
  it('should be able to make changes to account', async () => {
    const user = JSON.parse(JSON.stringify(mockUserData2))
    user.isAdmin = true
    user.username = 'marcus'
    user.email = 'kart@hva.nl'
    fetch.mockResponseOnce(JSON.stringify(user), {
      status: 200,
      statusText: 'OK',
      headers: {
        'Content-Type': 'application/json'
      },
      method: 'PUT'
    })
    const response = await usersServices.asyncSave(user)
    expect(response.username).toBe('marcus')
    expect(response.email).toBe('kart@hva.nl')
    expect(response.isAdmin).toBe(true)
  })
})

const mockedUsers = [
  {
    user_id: 1,
    sector_id: 1,
    first_name: 'Pim',
    last_name: 'schuu',
    email: '',
    security_clearance: 1,
    password: 'pimsch',
    username: '=',
    bio: null,
    occupation: null,
    date_of_birth: null,
    postalcode: null,
    user_goal: null,
    actionplans: [],
    img_path: null,
    isAdmin: true
  },
  {
    user_id: 2,
    sector_id: 1,
    first_name: 'Hans',
    last_name: 'Groot',
    email: 'h@test.nl',
    security_clearance: 1,
    password: 'hans',
    username: 'hansmans',
    bio: 'leeg',
    occupation: 'idk',
    date_of_birth: '2001-03-07',
    postalcode: null,
    user_goal: null,
    actionplans: [],
    img_path: null,
    isAdmin: true
  },
  {
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
  },
  {
    user_id: 10,
    sector_id: 0,
    first_name: 'Sally',
    last_name: 'Taylor',
    email: 'test123@gmail.com',
    security_clearance: 1,
    password: 'Staylor123!',
    username: 'sTaylor37',
    bio: 'I want to eat grass!',
    occupation: 'Software Engineer',
    date_of_birth: '2002-06-20',
    postalcode: '1225ST',
    user_goal: null,
    actionplans: [],
    img_path: 'http://localhost:8085/images/97bd47cd-9f80-419c-8d8c-9e3018616469.jpg',
    isAdmin: false
  }]

const mockUserData1 = {
  user_id: 101,
  sector_id: 1,
  first_name: 'Bas',
  last_name: 'Groote',
  email: 'BG@gmail.com',
  security_clearance: 1,
  password: 'bastiaan123',
  username: 'basgroote',
  bio: 'leeg',
  occupation: 'idk',
  date_of_birth: '2001-03-07',
  postalcode: null,
  img_path: null,
  isAdmin: true
}
const mockUserData2 = {
  user_id: 102,
  sector_id: 1,
  first_name: 'Arthur',
  last_name: 'Klein',
  email: 'AK@gmail.com',
  security_clearance: 1,
  password: 'arthur123',
  username: 'arthurklein',
  bio: 'leeg',
  occupation: 'idk',
  date_of_birth: '2001-03-07',
  postalcode: null,
  img_path: null,
  isAdmin: false
}

const mockUsers = [mockUserData1, mockUserData2]

afterEach(function () {
  wrapper.unmount() // Clean up the wrapper after each test
  wrapper = null
})
