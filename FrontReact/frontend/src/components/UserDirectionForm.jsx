import { NavLink } from "react-router-dom";

export default function UserDirectionForm({
  formData,
  onChange,
  onSubmit,
  loading,
}) {
  return (
    <form onSubmit={onSubmit} className="user-direction-form">
      <h4>Dirección de entrega</h4>

      <div>
        <label>Calle</label>
        <input
          type="text"
          name="streetName"
          value={formData.streetName}
          onChange={onChange}
          required
        />
      </div>

      <div>
        <label>Número</label>
        <input
          type="text"
          name="doorNumber"
          value={formData.doorNumber}
          onChange={onChange}
          required
        />
      </div>

      <div>
        <label>Teléfono</label>
        <input
          type="text"
          name="phone"
          value={formData.phone}
          onChange={onChange}
          required
        />
      </div>

      <div>
        <label>Comentarios</label>
        <input
          type="text"
          name="comments"
          value={formData.comments}
          onChange={onChange}
        />
      </div>

      {/*A FUTURO: método de pago */}
      <div>
        <label>Método de pago</label>
        <select
          name="paymentMethod"
          value={formData.paymentMethod}
          onChange={onChange}
        >
          <option value="">Seleccionar</option>
          <option value="CASH">Efectivo</option>
          <option value="MERCADO_PAGO">Mercado Pago</option>
        </select>
      </div>

      <button type="submit" disabled={loading}>
        {loading ? "Guardando..." : "Guardar dirección"}
      </button>
    </form>
  );
}
