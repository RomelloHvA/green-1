import { mount } from '@vue/test-utils'
import LogInView from '@/views/LogInView'
import router from '@/router'
import { SessionSbService } from '@/services/SessionSbService'
import CONFIG from '../../app-config'
import { reactive } from 'vue'

let wrapper

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
describe('Trying to login', () => {
  it('logs in with valid user credentials', async function () {
    // Verifying whether the input field for username and password exists
    const userNameField = wrapper.find('input#userName')
    const passwordField = wrapper.find('input#password')
    const loginButton = wrapper.find('button#loginButton')
    expect(userNameField.exists(),
      'Cannot find input field with placeholder = Username').toBe(true)
    expect(passwordField.exists(),
      'Cannot find input field with placeholder = Password').toBe(true)
    expect(loginButton.exists(),
      'Cannot find button with id = loginButton').toBe(true)

    // Attempt to log in with valid user credentials
    await userNameField.setValue('User')
    await passwordField.setValue('1234')
    await loginButton.trigger('click')

    // Error message should not exist when login attempt is successful
    const errorMessageText = wrapper.find('p.text-danger')
    console.log(errorMessageText)
    // expect(wrapper.vm.errorMessage,
    //   'Login attempt failed').toBe('')
  })
  it('logs in with an invalid username', async function () {
    const userNameField = wrapper.find('input#userName')
    const passwordField = wrapper.find('input#password')
    const loginButton = wrapper.find('button#loginButton')
    // Attempt to log in with valid user credentials
    await userNameField.setValue('sTaylor38')
    await passwordField.setValue('Staylor123!')
    await loginButton.trigger('click')
    await wrapper.vm.$nextTick()
    const errorMessageText = wrapper.find('p.text-danger')
    console.log(errorMessageText)
  })
})

afterEach(function () {
  wrapper.unmount() // Clean up the wrapper after each test
  wrapper = null
})
