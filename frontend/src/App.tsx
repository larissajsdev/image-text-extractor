import { useEffect, useRef, useState } from 'react'

import './App.css'
import { api } from './service/api';
import useImgConvertUpload from './service/hooks/useUploadFile';


function changeInput() {
  return <h1>Tem algo aqui</h1>
}

type FileData = {
  name: string;
  size: number;
  content: string | Blob;
}
export default function App() {
  const [file, setFile] = useState<FileData | null>(null);

  const [imagePreviewUrl, setImagePreviewUrl] = useState('');
  const [loading, setIsLoading] = useState(false);
  const [fileUploaded, setFileUploaded] = useState(false);
  const { textExtracted, error, progress, isLoaded, extractTextFromFile } = useImgConvertUpload();


  async function handleSubmit(e: any) {
    e.preventDefault();
    if (file)
      extractTextFromFile(file.content)

  }

  function removeFile() {
    setFile(null);
  }

  const handleFileUpload = (event: any) => {

    const file = event.target.files[0];

    const reader = new FileReader();

    setImagePreviewUrl(URL.createObjectURL(file));
    setIsLoading(true);

    reader.onload = () => {

      setFile({
        name: file.name,
        size: file.size,
        content: file
      })

      setFileUploaded(true);
      setIsLoading(false);
    };
    reader.readAsText(file);
  };

  useEffect(() => {
    console.log(file)
    console.log(imagePreviewUrl)
  }, [file, imagePreviewUrl])


  return (
    <div className="
        min-h-screen
        min-w-screen
        flex  
        justify-center 
        from-purple-600 to-blue-600 bg-gradient-to-tr inset-0 z-0
        items-center
        text-center
        ">
      <div className="sm:max-w-lg w-full p-10 bg-white rounded-xl z-10">
        <div className="text-center">
          <h2 className="mt-5 text-3xl font-bold text-gray-900">
            Image text <i>extractor</i>
          </h2>
          <p className="mt-2 text-sm text-gray-400">Extract all text from your image</p>
        </div>
        <form onSubmit={handleSubmit} className="mt-8 space-y-3" >
          <div className="grid grid-cols-1 space-y-2">
            <div className="flex items-center justify-center w-full">

              <label className="flex flex-col rounded-lg border-4 border-dashed w-full h-60 p-10 group text-center">
                <div className="h-full w-full text-center flex flex-col  justify-center items-center  ">
                  {!textExtracted ? <>
                    <div className="flex flex-auto max-h-48 w-2/5 mx-auto -mt-10">
                      {!file && <img className="has-mask h-36 object-center" src="https://img.freepik.com/free-vector/image-upload-concept-landing-page_52683-27130.jpg?size=338&ext=jpg" alt="freepik image" />}
                    </div>
                    {file && imagePreviewUrl && (
                      <div>
                        <img className="has-mask h-36 object-center" src={imagePreviewUrl} alt="freepik image" />
                      </div>
                    )}
                    {!file && <p className="pointer-none text-gray-500 ">For extract text from your image,<br /> <a id="" className="text-blue-600 hover:underline">select an image</a> from your computer</p>}
                    {loading && <p>Carregando arquivo...</p>}
                    {file && fileUploaded && <p >Arquivo coletado com sucesso!</p>}
                  </> : <p>convertido com sucesso!</p>}
                </div>
                <input accept="image/*" onChange={handleFileUpload} name="file" type="file" className="hidden" />
              </label>
            </div>
          </div>

          {file && <div className="d-grid gap-2">
            <button onClick={removeFile} type="button" className="btn btn-primary">Button</button>
          </div>}
          <p className="text-sm text-gray-300">
            <span>File type: doc,pdf,types of images</span>
          </p>
          <div>
            <button type="submit" className="my-5 w-full flex justify-center bg-blue-500 text-gray-100 p-4  rounded-full tracking-wide
                                  font-semibold  focus:outline-none focus:shadow-outline hover:bg-blue-600 shadow-lg cursor-pointer transition ease-in duration-300">
              Upload
            </button>
          </div>
        </form>
      </div>


      {textExtracted ? <div className="sm:max-w-lg w-full p-10 bg-white rounded-xl z-10">

        <label className="flex flex-col rounded-lg border-4 border-dashed w-full h-60 p-10 group text-center">
          <div dangerouslySetInnerHTML={{ __html: textExtracted }}></div>

        </label>
      </div> : <div></div>}


    </div>
  )

}