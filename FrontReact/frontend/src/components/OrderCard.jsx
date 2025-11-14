// src/components/OrderCard.jsx
import { useDispatch } from "react-redux";
import { updateOrder } from "../redux/features/dashboardSlice";
import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import { fetchOrderServiceFromApi } from "../services/orderService";

export default function OrderCard({ order, columnId }) {
  const dispatch = useDispatch();

  console.log("ordenes: ",order,"columna: " ,columnId);
  const onDragStart = (e) => {
    e.dataTransfer.setData("orderId", String(order.id));
    e.dataTransfer.setData("sourceColId", String(columnId));
  };

  const mesa = order.table?.name ?? "—";


  const handleDetails = async () => {
    try {
      // Modal de carga
      Swal.fire({
        title: "Cargando detalles...",
        didOpen: () => Swal.showLoading(),
        allowOutsideClick: false,
        
      });

      const items = await fetchOrderServiceFromApi(order.id);

      // Total del pedido (precio unitario * cantidad)
      const total = items.reduce(
        (acc, it) => acc + it.itemAmount * it.quantity,
        0
      );

      // Tiempo estimado
      const estimatedFromItems = items.reduce(
        (max, it) => Math.max(max, it.delayTime ?? 0),
        0
      );
      const estimatedMinutes =
        order.delayTime != null ? order.delayTime : estimatedFromItems;

      const rowsHtml = items
        .map((it) => {
          const nombre = it.menuItem?.name ?? "-";
          const cantidad = it.quantity;
          const unit = it.itemAmount;
          const subtotal = unit * cantidad;

          return `
            <tr>
              <td>${nombre}</td>
              <td class="text-center">${cantidad}</td>
              <td class="text-end">$${unit}</td>
              <td class="text-end">$${subtotal}</td>
            </tr>
          `;
        })
        .join("");

      const html = `
        <p><strong>Mesa:</strong> ${mesa}</p>
        ${
          estimatedMinutes > 0
            ? `<p><strong>Tiempo estimado:</strong> ${estimatedMinutes} min</p>`
            : `<p><strong>Tiempo estimado:</strong> No definido</p>`
        }
        <hr />
        ${
          items.length === 0
            ? "<p class='text-muted'>Este pedido no tiene ítems.</p>"
            : `
          <div class="table-responsive">
            <table class="table table-sm align-middle text-start">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th class="text-center">Cant.</th>
                  <th class="text-end">Unit.</th>
                  <th class="text-end">Subtotal</th>
                </tr>
              </thead>
              <tbody>
                ${rowsHtml}
              </tbody>
              <tfoot>
                <tr>
                  <th colspan="3" class="text-end">Total</th>
                  <th class="text-end">$${total}</th>
                </tr>
              </tfoot>
            </table>
          </div>
        `
        }
      `;

      Swal.fire({
        title: `Pedido #${order.id}`,
        html,
        width: 700,
        confirmButtonText: "Cerrar",
      });
    } catch (err) {
      console.error(err);
      Swal.fire({
        icon: "error",
        title: "Error",
        text: err.message || "No se pudieron cargar los detalles",
      });
    }
  };

  return (
    <div
      className="card p-2 shadow-sm"
      draggable
      onDragStart={onDragStart}
      style={{ cursor: "grab", minHeight: "70px" }}
    >
      <h6 className="m-0">#{order.id}</h6>

      <p className="m-0 text-muted small">
        Mesa: {mesa} 
      </p>

      <button
        className="btn btn-sm btn-outline-primary mt-1"
        onClick={handleDetails}
      >
        Ver detalles
      </button>
    </div>
  );
}
