import { useDispatch, useSelector } from "react-redux";
import { closeCartModal, confirmCartAsync, closeCartAsync } from "../redux/features/cartSlice";
import { useNavigate } from "react-router-dom";

export default function CartModal() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { isCartModalOpen, items, totalAmount, loading, error } = useSelector(
    (state) => state.cart
  );

  if (!isCartModalOpen) return null;

  const handleConfirm = async () => {
    const order = await dispatch(confirmCartAsync());
    if (order) {
      dispatch(closeCartModal());
      navigate("/dashboard"); // Redirigir al tablero
    }
  };

  const handleClose = async () => {
    await dispatch(closeCartAsync());
    dispatch(closeCartModal());
  };

  return (
    <div className="modal show d-block" tabIndex="-1" role="dialog">
      <div className="modal-dialog modal-dialog-end" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">Carrito</h5>
            <button
              type="button"
              className="btn-close"
              onClick={() => dispatch(closeCartModal())}
            />
          </div>

          <div className="modal-body">
            {loading && <p>Cargando...</p>}
            {error && <p className="text-danger">{error}</p>}

            {items.length === 0 && !loading && <p>No hay productos.</p>}

            {items.length > 0 && (
              <ul className="list-group mb-3">
                {items.map((item, index) => (
                  <li
                    key={`${item.cartItemId}-${index}`}
                    className="list-group-item d-flex justify-content-between"
                  >
                    <div>
                      <strong>{item.name}</strong>
                      <div className="small">Cantidad: {item.quantity}</div>
                    </div>
                    <span>${Number(item.unitPrice) * item.quantity}</span>
                  </li>
                ))}
              </ul>
            )}

            <div className="d-flex justify-content-between mt-3">
              <strong>Total:</strong>
              <span>${totalAmount}</span>
            </div>
          </div>

          <div className="modal-footer">
            <button
              className="btn btn-secondary"
              onClick={() => dispatch(closeCartModal())}
            >
              Volver
            </button>

            <button
              className="btn btn-outline-danger"
              onClick={handleClose}
              disabled={items.length === 0 || loading}
            >
              Cerrar carrito
            </button>

            <button
              className="btn btn-primary"
              onClick={handleConfirm}
              disabled={items.length === 0 || loading}
            >
              Confirmar pedido
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
