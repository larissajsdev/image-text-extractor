import { useState } from "react";
import axios from "axios";
import { api } from "../api";

function useImgConvertUpload() {
    const [textExtracted, setTextExtracted] = useState<string| null>("");
    const [error, setError] = useState(null);
    const [progress, setProgress] = useState(0);
    const [isLoaded, setIsLoaded] = useState(false);

    const extractTextFromFile = async (file: any) => {
        const formData = new FormData();
        formData.append("img", file);

        try {

            const response = await api.post("api/ocr/image-text", formData, {
                onUploadProgress: (event: any) => {
                    let progress: number = Math.round(
                        (event.loaded * 100) / event.total
                    );
                    setProgress(progress)
                    console.log(
                        `A imagem ${"filename"} está ${progress}% carregada... `
                    );
                }
            })

            let formattedText = "<pre>".concat(response.data).concat("</pre>") ;
            

            setTextExtracted(formattedText);
            setError(null);
            setIsLoaded(true);
        } catch (err: any) {
            setTextExtracted(null);
            setError(err.message || "Upload failed");
            setProgress(0);
        }
    };

    return { textExtracted, error, progress, isLoaded, extractTextFromFile };
}

export default useImgConvertUpload;
