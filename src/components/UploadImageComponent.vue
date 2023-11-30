<template>
    <div class="container col-md-6">
        <img id="frame" :src="imgSource" class="img-fluid" />
        <div class="mb-5">
            <input class="form-control" type="file" id="formFile" @change="preview">
            <button @click="clearImage" class="btn btn-primary mt-3">Click me</button>
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

const emit = defineEmits(['image-previewed'])

const imgSource = ref(props.imagePath)
const currentImage = ref(null)

const uploadImage = async () => {
  const formData = new FormData()
  formData.append('file', currentImage.value)

  const { load, data } = useFetch(CONFIG.BACKEND_URL + '/upload', currentImage.value, 'POST')

  // Call the load function with the FormData, set the method to POST
  await load()

  // Handle the response, e.g., saving the returned image path or URL
  if (data.value) {
    console.log(data.value)
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
}

defineExpose({
  uploadImage
})

</script>

<style></style>
