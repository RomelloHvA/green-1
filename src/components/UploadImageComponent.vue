<template>
    <div class="container col-md-6 uploadComponent">
      <figure class="imgFigurePreview justify-content-center m-auto">
        <img id="frame" :src="imgSource" class="img-fluid my-2 imgFit" />
      </figure>
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
    object-fit: contain;
    max-height: 100%;
    max-width: 100%;
 }

 .imgFigurePreview {
    width: inherit;
    height: inherit;
 }

 .uploadComponent {
    object-fit: contain;
 }
</style>
