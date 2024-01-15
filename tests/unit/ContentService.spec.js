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
describe('Method findAllPages', () => {
  it('should return all pages their content', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON))
    const result = await contentService.findAllPages()
    expect(result.pages.value.length).toBe(4)
    expect(result.isPending.value).toBeFalsy()
    expect(result.error.value).toBeNull()
  })
  it('should return an error when the fetch fails', async function () {
    fetch.mockRejectOnce(new Error('fake error message'))
    const result = await contentService.findAllPages()
    expect(result.pages.value.length).toBe(0)
    expect(result.isPending.value).toBeFalsy()
    expect(result.error.value).toBe('fake error message')
  })
  it('should return empty array', async function () {
    fetch.mockResponseOnce(JSON.stringify([]))
    const result = await contentService.findAllPages()
    expect(result.pages.value).toStrictEqual([])
    expect(result.isPending.value).toBeFalsy()
    expect(result.error.value).toBeNull()
  })
})

describe('Method findContentByPageId', () => {
  it('should return all content for a given pageId', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON))
    const result = await contentService.findContentByPageId(1)
    expect(result.editableContent.value.length).toBe(4)
    expect(result.isPending.value).toBeFalsy()
    expect(result.error.value).toBeNull()
  })
  it('should return an error when the fetch fails', async function () {
    fetch.mockRejectOnce(new Error('fake error message'))
    const result = await contentService.findContentByPageId(1)
    expect(result.editableContent.value.length).toBe(0)
    expect(result.isPending.value).toBeFalsy()
    expect(result.error.value).toBe('fake error message')
  })
  it('should return empty array', async function () {
    fetch.mockResponseOnce(JSON.stringify([]))
    const result = await contentService.findContentByPageId(1)
    expect(result.editableContent.value).toStrictEqual([])
    expect(result.isPending.value).toBeFalsy()
    expect(result.error.value).toBeNull()
  })
})

describe('Method saveContentById', () => {
  it('should return a success message', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON))
    const result = await contentService.saveContentById(mockContentJSON, 1)
    expect(result.succes.value).toBeTruthy()
  })
  it('should return an error when the fetch fails', async function () {
    fetch.mockRejectOnce(new Error('fake error message'))
    const result = await contentService.saveContentById(mockContentJSON, 1)
    expect(result.error.value).toBe('fake error message')
  })
  it('should return an error with the response status when response is not ok', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON), { status: 400 })
    const result = await contentService.saveContentById(mockContentJSON, 1)
    expect(result.error.value).toContain('400')
  })
  it('Should return error for not finding the pageId', async function () {
    fetch.mockResponseOnce(JSON.stringify(mockContentJSON), { status: 404 })
    const result = await contentService.saveContentById(mockContentJSON, 1)
    expect(result.error.value).toContain('404')
  })
})

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
