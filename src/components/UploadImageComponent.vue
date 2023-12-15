<template>
    <div class="container col-md-6">
      <img id="frame" :src="imgSource" class="img-fluid my-2 imgFit" />
      <div class="mb-5">
        <input class="form-control" type="file" id="formFile" @change="preview">
        <button @click="clearImage" class="btn btn-primary mt-3">Remove Image</button>
      </div>
    </div>
</template>

<script setup>
import { ref, defineProps, defineExpose, defineEmits } from 'vue'
import { useFetch } from '@/utils/useFetch'
import CONFIG from '@/app-config'

const props = defineProps({
  imagePath: String
})

const emit = defineEmits(['image-previewed', 'image-cleared'])

const imgSource = ref(props.imagePath)
const currentImage = ref(null)

/**
 * This function uploads the image to the backend.
 * You can use this function in your own component by stating ref="uploadImage" in the component tag. and then uploadImage.value.uploadImage()
 * @returns {string} Returns the path of the uploaded image.
 * @author Marco de Boer
 */

const uploadImage = async () => {
  const formData = new FormData()
  formData.append('file', currentImage.value)

  const { load, data } = useFetch(CONFIG.BACKEND_URL + '/upload', formData, 'POST')

  // Call the load function with the FormData, set the method to POST
  await load()

  // Handle the response, e.g., saving the returned image path or URL
  if (data.value) {
    return data.value
  }
}

const preview = (event) => {
  currentImage.value = event.target.files[0]
  imgSource.value = URL.createObjectURL(currentImage.value)
  emit('image-previewed', imgSource.value)
}

const clearImage = () => {
  imgSource.value = ''
  emit('image-cleared')
}

defineExpose({
  uploadImage
})

</script>

<style>
 .borderRed {
   border: 1px solid red;
 }

 .imgFit {
  max-width: 100%; /* Image can grow up to container's width */
  max-height: 100%; /* Image can grow up to container's height */
  object-fit: contain; /* Maintain aspect ratio within the bounds */
}

 .imgFigurePreview {
    width: inherit;
    height: inherit;
 }
</style>
