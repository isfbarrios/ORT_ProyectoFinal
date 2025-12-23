// src/components/UploadMenuExcel.jsx
import { useRef, useState } from "react";
import { uploadMenuExcelService } from "../services/menuService";

function UploadMenuExcel({ onImported }) {
  const fileInputRef = useRef(null);
  const [loading, setLoading] = useState(false);
  const [mensaje, setMensaje] = useState(null);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMensaje(null);
    setError(null);

    const file = fileInputRef.current?.files?.[0];
    if (!file) {
      setError("Seleccioná un archivo Excel.");
      return;
    }

    try {
      setLoading(true);
      const respuesta = await uploadMenuExcelService(file);
      setMensaje(respuesta);

      // si el padre pasa un callback para refrescar la lista, lo ejecutamos
      if (typeof onImported === "function") {
        onImported();
      }
    } catch (err) {
      setError(err.message || "Error al importar el menú.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="card p-3 mt-3">
      <h5 className="mb-3">Cargar menú desde Excel</h5>

      <form onSubmit={handleSubmit}>
        <div className="mb-2">
          <input
            type="file"
            ref={fileInputRef}
            accept=".xlsx,.xls"
            className="form-control"
          />
        </div>

        <button
          type="submit"
          className="btn btn-primary"
          disabled={loading}
        >
          {loading ? "Importando..." : "Importar menú"}
        </button>
      </form>

      {mensaje && (
        <p className="mt-2 text-success">
          {mensaje}
        </p>
      )}

      {error && (
        <p className="mt-2 text-danger">
          {error}
        </p>
      )}
    </div>
  );
}

export default UploadMenuExcel;
