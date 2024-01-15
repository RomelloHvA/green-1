import { enableFetchMocks } from 'jest-fetch-mock'
import { UsersAdaptor } from '@/services/users-adaptor'
import CONFIG from '@/app-config'
import { reactive } from 'vue'

enableFetchMocks()

const usersServices = reactive(
  new UsersAdaptor(CONFIG.BACKEND_URL + '/users'))

beforeEach(() => {
  fetch.resetMocks()
})

/**
 * Test for user-adaptor.js
 */
describe('Method findAll', () => {
  it('should return all users', async () => {
    fetch.mockResponseOnce(JSON.stringify(mockUsers),
      {
        status: 200,
        statusText: 'OK',
        headers: { 'Content-Type': 'application/json' },
        method: 'GET'
      })
    const response = await usersServices.asyncFindAll()
    expect(response).toEqual(mockUsers)
  })
})

describe('Method findById', () => {
  it('should return a user account with given id', async () => {
    fetch.mockResponseOnce(JSON.stringify(mockUsers[0]),
      {
        status: 200,
        statusText: 'OK',
        headers: { 'Content-Type': 'application/json' },
        method: 'GET'
      })
    const response = await usersServices.asyncFindById(1)
    expect(response).not.toBeNull()
    expect(response.length, 'a single user should not be returned in a Array').toBeUndefined()
    expect(response.user_id, 'the user account does not match the given id').toStrictEqual(mockUsers[0].user_id)
  })
})

describe('Method save', () => {
  it('should return a user with new data', async () => {
    const user = {
      user_id: 1,
      first_name: 'Carl',
      last_name: 'Simons',
      username: 'carlsimons',
      email: 'CarlSims@gmail.com',
      password: 'bastiaan123',
      occupation: 'Student',
      date_of_birth: '2001-03-07',
      postalcode: '1234AA'
    }
    fetch.mockResponseOnce(JSON.stringify(user), {
      status: 200,
      statusText: 'OK',
      headers: { 'Content-Type': 'application/json' },
      method: 'POST'
    })
    const response = await usersServices.asyncSave(user)
    expect(response).not.toBeNull()
    expect(response.username).toStrictEqual('carlsimons')
    expect(response.first_name).toStrictEqual('Carl')
    expect(response.last_name).toStrictEqual('Simons')
    expect(response.email).toStrictEqual('CarlSims@gmail.com')
    expect(response.password).toStrictEqual('bastiaan123')
    expect(response.occupation).toStrictEqual('Student')
    expect(response.date_of_birth).toStrictEqual('2001-03-07')
    expect(response.postalcode).toStrictEqual('1234AA')
  })
})
describe('Method updateProfile', () => {
  it('should updaten a profile with new data', async () => {
    const user = mockUserData1
    user.first_name = 'Sarah'
    user.last_name = 'Dessert'
    user.occupation = 'Baker'
    fetch.mockResponseOnce(JSON.stringify(user), {
      status: 200,
      statusText: 'OK',
      headers: { 'Content-Type': 'application/json' },
      method: 'PUT'
    })
    const response = await usersServices.asyncUpdateProfile(user, user.user_id)
    expect(response, 'updating a users profile should not return nothing').not.toBeNull()
    expect(response.first_name, 'the first name should not be the same as the one before the update').toStrictEqual('Sarah')
    expect(response.last_name, 'the last name should not be the same as the one before the update').toStrictEqual('Dessert')
    expect(response.occupation, 'the occupation should not be the same as the one before the update').toStrictEqual('Baker')
  })
})
describe('Method deleteById', () => {
  it('should delete a user with given id', async () => {
    const deletedUser = mockUserData1
    fetch.mockResponseOnce(JSON.stringify(mockUserData1), {
      status: 200,
      statusText: 'OK',
      headers: { 'Content-Type': 'application/json' },
      method: 'DELETE'
    })
    const response = await usersServices.asyncDeleteById(deletedUser.user_id)
    expect(response.length, 'deleting a user should not return an array').toStrictEqual(undefined)
    expect(response.user_id, 'deleted user should only be deleted with the same id').toStrictEqual(mockUsers[0].user_id)
  })
})

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

const mockUsers = [mockUserData1, mockUserData2]
