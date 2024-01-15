import { mount } from '@vue/test-utils'
import QuizQuestionYesNoComponent from '@/components/quiz/QuizQuestionYesNoComponent'
import YesNoQuestion from '@/models/YesNoQuestion'

let wrapper

beforeEach(async function () {
  wrapper = mount(QuizQuestionYesNoComponent, {
    propsData: {
      questionObject: question
    }
  })
})

/**
 * Test for quizquestionyesno component in src/components/quiz/QuizQuestionYesNoComponent.vue
 * @author Marco de Boer
 */
describe('On mounting the component', () => {
  it('should have a questionObject as a prop', () => {
    expect(wrapper.props().questionObject, 'there is no questionObject prop').toBeTruthy()
  })
  it('should have the question displayed as a title', () => {
    expect(wrapper.find('h1').text(), 'the question as title is not the same giving in the prop')
      .toBe(question.question)
  })
  it('should have the imgPath displayed as an image', () => {
    expect(wrapper.find('img').attributes('src'), 'the image is not the same giving in the prop')
      .toBe(question.imgPath)
  })
  it('should have no img if imgPath is null', async () => {
    await wrapper.setProps({ questionObject: new YesNoQuestion(1, 1, 'question', null, 1) })
    expect(wrapper.find('img').exists(), 'there is an image while imgPath is null').toBeFalsy()
  })
  describe('the 2 buttons', () => {
    it('should exist', () => {
      const buttons = wrapper.findAll('button')
      expect(buttons.at(0).text(), 'the first button is not yes').toBe('Yes')
      expect(buttons.at(1).text(), 'the second button is not no').toBe('No')
      expect(buttons.at(3), 'there are more than 2 buttons').toBeUndefined()
    })
    it('should not be selected', () => {
      const buttons = wrapper.findAll('button')
      expect(buttons.at(0).classes('selectedButton'), 'the first button is selected').toBeFalsy()
      expect(buttons.at(1).classes('selectedButton'), 'the second button is selected').toBeFalsy()
    })
    it('if given answer is true the first button should be selected', async () => {
      await wrapper.props().questionObject.setGivenAnswer(true)
      const buttons = wrapper.findAll('button')
      expect(buttons.at(0).classes('selectedButton'), 'the first button is not selected').toBeTruthy()
      expect(buttons.at(1).classes('selectedButton'), 'the second button is selected').toBeFalsy()
    })
    it('if given answer is false the second button should be selected', async () => {
      await wrapper.props().questionObject.setGivenAnswer(false)
      const buttons = wrapper.findAll('button')
      expect(buttons.at(0).classes('selectedButton'), 'the first button is selected').toBeFalsy()
      expect(buttons.at(1).classes('selectedButton'), 'the second button is not selected').toBeTruthy()
    })
  })
})

describe('When clicking on a answer', () => {
  it('should set the givenAnswer to true when clicking on the first button', async () => {
    await wrapper.find('button').trigger('click')
    expect(wrapper.props().questionObject.givenAnswer, 'the givenAnswer is not true').toBeTruthy()
  })
  it('should set the givenAnswer to false when clicking on the second button', async () => {
    await wrapper.findAll('button').at(1).trigger('click')
    expect(wrapper.props().questionObject.givenAnswer, 'the givenAnswer is not false').toBeFalsy()
  })
  it('should emit questionAnswered event when clicking on a button', async () => {
    await wrapper.find('button').trigger('click')
    expect(wrapper.emitted().questionAnswered, 'the questionAnswered event is not emitted').toBeTruthy()
  })
})

afterEach(function () {
  wrapper.unmount() // Clean up the wrapper after each test
  wrapper = null
})

const questionJSON = {
  id: 1,
  question: 'Do you believe that providing affordable and clean energy is a key challenge for the Netherlands?',
  imgPath: 'http://localhost:8085/images/c00bcccb-9c49-47b0-b650-50076734a2e1.JPEG',
  sdg: 7,
  index: 3,
  type: 'yesno',
  givenAnswer: null
}

const question = new YesNoQuestion(questionJSON.id, questionJSON.index, questionJSON.question, questionJSON.imgPath, questionJSON.sdg)
