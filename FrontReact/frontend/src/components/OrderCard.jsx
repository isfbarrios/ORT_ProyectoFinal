// src/components/dashboard/OrderCard.jsx
import { useDispatch } from "react-redux";
import { updateOrder } from "../redux/features/dashboardSlice";

export default function OrderCard({ order, columnId }) {
  const dispatch = useDispatch();

  const onDragStart = (e) => {
    e.dataTransfer.setData("orderId", order.id);
    e.dataTransfer.setData("sourceColId", columnId);
  };

  return (
    <div
      className="card p-2 shadow-sm"
      draggable
      onDragStart={onDragStart}
      style={{ cursor: "grab", minHeight: "70px" }}
    >
      <h6 className="m-0">#{order.number}</h6>
      <p className="m-0 text-muted small">
        Mesa: {order.table ?? "—"} · Canal: {order.channel}
      </p>

      <button
        className="btn btn-sm btn-outline-primary mt-1"
        
        onClick={() =>
          dispatch(updateOrder({ id: order.id, changes: { viewed: true } }))
        }
      >
        Ver detalles
      </button>
    </div>
  );
}
