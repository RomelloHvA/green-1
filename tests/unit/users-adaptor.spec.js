import CONFIG from '../../app-config'
import { enableFetchMocks } from 'jest-fetch-mock'
import { UsersAdaptor } from '@/services/users-adaptor'
enableFetchMocks()

const usersService = new UsersAdaptor(CONFIG.BACKEND_URL)

beforeEach(() => {
  fetch.resetMocks()
})
/**
 * Test for UserAdaptor
 * @author Jiaming Yan
 */
describe('Finding all users', () => {
  it('should return all users', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockedUsers), { status: 200, statusText: 'OK', headers: { 'Content-Type': 'application/json' }, method: 'GET' })
    const response = await usersService.asyncFindAll()
    expect(response.length, 'the amount of users is incorrect').toStrictEqual(7)
  })
})
describe('Finding one user', () => {
  it('should return a user with the given id', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockedUsers[0]), { status: 200, statusText: 'OK', headers: { 'Content-Type': 'application/json' }, method: 'GET' })
    const response = await usersService.asyncFindById(1)
    expect(response.length, 'retrieving a single user should not return an array').toStrictEqual(undefined)
    expect(response, 'the user does not match with the user in the users-list').toStrictEqual(mockedUsers[0])
    expect(response.user_id, 'the user id does not match with the user id in the users-list').toStrictEqual(mockedUsers[0].user_id)
  })
})
describe('Saving/updating an user', () => {
  it('should return the saved/updated user', async function () {
    const updatedUser = { ...mockedUsers[0] }
    updatedUser.username = 'pSchuu31'
    fetch.mockResponseOnce(JSON.stringify(updatedUser), {
      status: 200,
      statusText: 'OK',
      headers: { 'Content-Type': 'application/json' },
      method: 'GET'
    })
    const response = await usersService.asyncSave(updatedUser)
    expect(response.length, 'updating an user should not return an array').toStrictEqual(undefined)
    expect(response.user_id, 'the user id should not change').toStrictEqual(mockedUsers[0].user_id)
    expect(response.username, 'the username should not the same after update').not.toStrictEqual(mockedUsers[0].username)
  })
})
describe('Deleting an user', () => {
  it('should return the deleted user', async function () {
    const userToDelete = mockedUsers[3]
    fetch.mockResponseOnce(JSON.stringify(userToDelete), { status: 200, statusText: 'OK', headers: { 'Content-Type': 'application/json' }, method: 'GET' })
    const response = await usersService.asyncDeleteById(userToDelete.user_id)
    expect(response.length, 'deleting an user should not return an array').toStrictEqual(undefined)
    expect(response.user_id, 'the user id should not change').toStrictEqual(mockedUsers[3].user_id)
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
  },
  {
    user_id: 13,
    sector_id: 0,
    first_name: 'Af',
    last_name: 'Fa',
    email: 'a@a.nl',
    security_clearance: 1,
    password: 'Aa123456!',
    username: '123456',
    bio: null,
    occupation: null,
    date_of_birth: '2008-01-06',
    postalcode: '1111AA',
    user_goal: null,
    actionplans: [],
    img_path: null,
    isAdmin: false
  },
  {
    user_id: 253,
    sector_id: 0,
    first_name: 'Mark',
    last_name: 'Rutte',
    email: 'm.rutte55@hotmail.com',
    security_clearance: 1,
    password: 'Kaas123!',
    username: 'mRutte55',
    bio: 'I absolutely love the weather in the Netherlands',
    occupation: 'Politician',
    date_of_birth: '1967-02-14',
    postalcode: '2055CP',
    user_goal: null,
    actionplans: [],
    img_path: 'http://localhost:8085/images/ecea2a33-9ef0-455b-8466-944da24b16a2.png',
    isAdmin: false
  },
  {
    user_id: 303,
    sector_id: 0,
    first_name: 'Mike',
    last_name: 'Oaxmall',
    email: 'mikeisawesome1275@gmail.com',
    security_clearance: 1,
    password: 'Mike2000!',
    username: 'Mike_Oaxmall',
    bio: 'Hallo',
    occupation: null,
    date_of_birth: null,
    postalcode: '1275ER',
    user_goal: null,
    actionplans: [],
    img_path: 'http://localhost:8085/images/91946986-6a43-4e3a-987e-43f1d085502d.jpg',
    isAdmin: false
  }
]
