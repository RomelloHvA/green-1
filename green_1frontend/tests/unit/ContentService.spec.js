import { enableFetchMocks } from 'jest-fetch-mock'
import { RESTContentAdaptor } from '@/services/RESTContentAdaptor'
import { CONFIG } from '@/CONFIG'

enableFetchMocks()

const contentService = new RESTContentAdaptor(CONFIG.BACKEND_URL + '/page')

beforeEach(() => {
  fetch.resetMocks()
})

/**
 * Test contentService
 * @author Romello ten Broeke
 */
// FIRST principle
describe('Method findAllPages', () => {
  it('should return all pages their content', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON))
    // act
    const result = await contentService.findAllPages()
    // assert
    expect(result.pages.value.length).toBe(4)
    // assert
    expect(result.isPending.value).toBeFalsy()
    // assert
    expect(result.error.value).toBeNull()
  })
  it('should return an error when the fetch fails', async function () {
    // arrange
    fetch.mockRejectOnce(new Error('fake error message'))
    // act
    const result = await contentService.findAllPages()
    // assert
    expect(result.pages.value.length).toBe(0)
    // assert
    expect(result.isPending.value).toBeFalsy()
    // assert
    expect(result.error.value).toBe('fake error message')
  })
  it('should return empty array', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify([]))
    // act
    const result = await contentService.findAllPages()
    // assert
    expect(result.pages.value).toStrictEqual([])
    // assert
    expect(result.isPending.value).toBeFalsy()
    // assert
    expect(result.error.value).toBeNull()
  })
})
// FIRST principle
describe('Method findContentByPageId', () => {
  it('should return all content for a given pageId', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON))
    // act
    const result = await contentService.findContentByPageId(1)
    // assert
    expect(result.editableContent.value.length).toBe(4)
    // assert
    expect(result.isPending.value).toBeFalsy()
    // assert
    expect(result.error.value).toBeNull()
  })
  it('should return an error when the fetch fails', async function () {
    // arrange
    fetch.mockRejectOnce(new Error('fake error message'))
    // act
    const result = await contentService.findContentByPageId(1)
    // assert
    expect(result.editableContent.value.length).toBe(0)
    // assert
    expect(result.isPending.value).toBeFalsy()
    // assert
    expect(result.error.value).toBe('fake error message')
  })
  it('should return empty array', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify([]))
    // act
    const result = await contentService.findContentByPageId(1)
    // assert
    expect(result.editableContent.value).toStrictEqual([])
    // assert
    expect(result.isPending.value).toBeFalsy()
    // assert
    expect(result.error.value).toBeNull()
  })
})
// FIRST principle
describe('Method saveContentById', () => {
  it('should return a success message', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON))
    // act
    const result = await contentService.saveContentById(mockContentJSON, 1)
    // assert
    expect(result.succes.value).toBeTruthy()
  })
  it('should return an error when the fetch fails', async function () {
    // arrange
    fetch.mockRejectOnce(new Error('fake error message'))
    // act
    const result = await contentService.saveContentById(mockContentJSON, 1)
    // assert
    expect(result.error.value).toBe('fake error message')
  })
  it('should return an error with the response status when response is not ok', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON), { status: 400 })
    // act
    const result = await contentService.saveContentById(mockContentJSON, 1)
    // assert
    expect(result.error.value).toContain('400')
  })
  it('Should return error for not finding the pageId', async function () {
    // arrange
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON), { status: 404 })
    // act
    const result = await contentService.saveContentById(mockContentJSON, 1)
    // assert
    expect(result.error.value).toContain('404')
  })
})
// arrange
const mockContentJSON = [
  {
    pageId: 1,
    pageTitle: 'Home Page Testing'
  },
  {
    pageId: 2,
    pageTitle: 'aboutUs Client Testing'
  },
  {
    pageId: 3,
    pageTitle: 'AboutUs Team Testing'
  },
  {
    pageId: 4,
    pageTitle: 'AboutUs Purpose Testing'
  }
]
