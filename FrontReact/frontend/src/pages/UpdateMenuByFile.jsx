import { useState } from "react";
import { updateMenuByFileAsync } from "../redux/features/menuSlice";
import { useDispatch } from "react-redux";

function FileUploadForm() {
  const dispatch = useDispatch();
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    if (!file) {
      alert("Seleccioná un archivo");
      return;
    }

    // Ejemplo de uso
    console.log("Archivo seleccionado:", file);
    console.log("Nombre:", file.name);
    console.log("Tamaño:", file.size);
    console.log("Tipo:", file.type);

    // Acá normalmente armarías el FormData
    // y lo enviarías al backend
    const formData = new FormData();
    formData.append("file", file);

    dispatch(updateMenuByFileAsync(formData));
  };

  return (
    <div style={{ maxWidth: "400px", margin: "2rem auto" }}>
      <h2>Cargar archivo</h2>

      <form onSubmit={handleSubmit}>
        <input
          type="file"
          onChange={handleFileChange}
        />

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enviar
        </button>
      </form>

      {file && (
        <div style={{ marginTop: "1rem" }}>
          <strong>Archivo seleccionado:</strong>
          <div>{file.name}</div>
        </div>
      )}
    </div>
  );
}

export default FileUploadForm;
