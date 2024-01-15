/**
 * Test for the Admin User Component
 * @Author Justin Chan
 */
import AdminUserComponent from '@/components/AdminDashboard/AdminUserComponent'
import { mount } from '@vue/test-utils'
import CONFIG from '../../app-config'
import { reactive } from 'vue'
import { UsersAdaptor } from '@/services/users-adaptor'

// eslint-disable-next-line no-unused-vars
let wrapper

const usersServices = reactive(
  new UsersAdaptor(CONFIG.BACKEND_URL))

const mockUserData1 = {
  user_id: 1,
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
  user_id: 1,
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
jest.mock('@/components/AdminDashboard/AdminUserComponent', () => ({
  name: 'MockAdminUserComponent',
  template: '<div>Mock AdminUserComponent Component</div>'
}))
const mockUsers = [mockUserData1, mockUserData2]
// const mockUsersServices = {
//   asyncFindAll: jest.fn().mockResolvedValue(mockUsers),
//   asyncSave: jest.fn().mockResolvedValue(mockUserData2)
// }
const mockSessionService = {
  currentAccount: Promise.resolve({ user_id: 1 }) // Mock current account data
}
beforeEach(async () => {
  fetch.resetMocks()

  wrapper = mount(AdminUserComponent, {
    global:
      {
        provide: {
          // usersServices: mockUsersServices,
          sessionService: mockSessionService
        }
      }
  })
})

/**
 * Test for the Admin User Component
 * @Author Justin Chan
 */
describe('Load all users from the database', () => {
  it('it should have a list of users', async () => {
    fetch.mockResponseOnce(JSON.stringify(mockUsers))
    const users = await usersServices.asyncFindAll()

    expect(users, 'users should exist').toBeTruthy()
    expect(users.length, 'users should have a length of at least 1').toBeGreaterThan(0)
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
