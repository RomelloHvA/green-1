import { mount } from '@vue/test-utils'
import LogInView from '@/views/LogInView'
import router from '@/router'
import { SessionSbService } from '@/services/SessionSbService'
import CONFIG from '../../app-config'
import { reactive } from 'vue'

let wrapper
const TITLE = 'Log In'
const USERNAME = 'Test User'
const PASSWORD = 'TestPassword123'

beforeEach(async function () {
  const sessionService = reactive(
    new SessionSbService(CONFIG.BACKEND_URL + '/authentication/login'))
  wrapper = mount(LogInView, {
    global: {
      plugins: [router],
      provide: {
        sessionService: sessionService
      }
    }
  })
})
/**
 * Test for user-login
 * @author Jiaming Yan
 */
describe('Mounting the component now...', () => {
  it('should have no props', () => {
    expect(wrapper.props(), 'This component should have no props').toStrictEqual({})
  })
  it('should have variables in the data hook', () => {
    expect(wrapper.vm.inputUserName, 'there is no variable for username').toBeDefined()
    expect(wrapper.vm.inputPassWord, 'there is no variable for password').toBeDefined()
    expect(wrapper.vm.errorMessage, 'there is no variable for errormessage').toBeDefined()
  })
  it('should have Log In as title', () => {
    expect(wrapper.find('h2').text(), 'the title is incorrect').toStrictEqual(TITLE)
  })
  it('should have an input field for username', () => {
    expect(wrapper.find('input#userName'), 'there is no input field for username').toBeTruthy()
  })
  it('should have an input field for password', () => {
    expect(wrapper.find('input#password'), 'there is no input field for password').toBeTruthy()
  })
})
describe('Login button', () => {
  it('should have a button for login', () => {
    expect(wrapper.find('button#loginButton'), 'there is no button for login').toBeTruthy()
  })
  it('should not disable button for login', () => {
    expect(wrapper.find('button#loginButton').isDisabled(), 'the button is disabled').toBeFalsy()
  })
})
describe('The input fields', () => {
  it('should update the variables in the data hook immediately', async function () {
    await wrapper.find('input#userName').setValue(USERNAME)
    await wrapper.find('input#password').setValue(PASSWORD)
    expect(wrapper.vm.inputUserName, 'the value does not match with the input field for username').toStrictEqual(USERNAME)
    expect(wrapper.vm.inputPassWord, 'the value does not match with the input field for username').toStrictEqual(PASSWORD)
  })
})

afterEach(function () {
  wrapper.unmount() // Clean up the wrapper after each test
  wrapper = null
})
