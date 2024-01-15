import UnknownRoute from '@/components/UnknownRoute'
import { mount } from '@vue/test-utils'

let wrapper
beforeEach(async function () {
  wrapper = mount(UnknownRoute)
})
/**
 * Test for UnknownRoute component
 * @author Jiaming Yan
 */
describe('UnknownRoute component', () => {
  it('should have no props', () => {
    expect(wrapper.props(), 'This component should have no props').toStrictEqual({})
  })
  it('should have a title', () => {
    expect(wrapper.find('h1').exists(), 'There should be a title').toBeTruthy()
  })
  it('should display an image to inform the user', () => {
    expect(wrapper.find('img.img-fluid').exists(), 'This component has no image at all').toBeTruthy()
  })
  it('should display an error message', () => {
    expect(wrapper.find('h4').exists(), 'This component is not displaying the error').toBeTruthy()
  })
  it('should have a button for returning to homepage', () => {
    expect(wrapper.find('button').exists(), 'This is no button for returning to homepage').toBeTruthy()
  })
})
describe('The button', () => {
  it('should not be disabled', () => {
    expect(wrapper.find('button').isDisabled(), 'button should not be disabled').toBeFalsy()
  })
  it('should display its purpose', () => {
    expect(wrapper.find('button').text(), 'button is not displaying anything').toBeTruthy()
    expect(wrapper.find('button').text(), 'button text is incorrect').toStrictEqual('Or just go back to home')
  })
})
