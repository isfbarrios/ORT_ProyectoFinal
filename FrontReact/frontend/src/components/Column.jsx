import { useDispatch, useSelector } from "react-redux";
import { moveOrder } from "../redux/features/dashboardSlice";
import OrderCard from "./OrderCard";
import Swal from "sweetalert2";
import { updateOrderState } from "../services/orderService"; // POST del backend

export default function Column({ column }) {
  const dispatch = useDispatch();
  const ordersById = useSelector((state) => state.dashboard.orders);

  const handleDrop = async (e) => {
    e.preventDefault();

    const orderIdStr = e.dataTransfer.getData("orderId");
    const sourceColIdStr = e.dataTransfer.getData("sourceColId");

    if (!orderIdStr || !sourceColIdStr) return;

    const orderId = Number(orderIdStr);
    const sourceColId = Number(sourceColIdStr);
    const targetColId = Number(column.id);

    // 1) Mover en el front inmediatamente (UX instantáneo)
    dispatch(
      moveOrder({
        orderId,
        sourceColId,
        targetColId,
      })
    );

    try {
      // 2) Actualizar en backend con POST (tu elección)
      await updateOrderState(orderId, targetColId);

      console.log(`Estado del pedido #${orderId} actualizado a columna ${targetColId}`);
      
    } catch (error) {
      console.error("Error backend:", error);

      // 3) Revertir cambio en el front si el backend falla
      dispatch(
        moveOrder({
          orderId,
          sourceColId: targetColId, // revertimos
          targetColId: sourceColId,
        })
      );

      Swal.fire({
        icon: "error",
        title: "Error",
        text: "No se pudo actualizar el estado del pedido",
      });
    }
  };

  const orderIds = column.orderIds || [];

  return (
    <div
      className="bg-light p-2 rounded shadow-sm"
      style={{ width: "280px", minHeight: "70vh" }}
      onDragOver={(e) => e.preventDefault()}
      onDrop={handleDrop}
    >
      <h5 className="mb-3 text-center">{column.title}</h5>

      <div className="d-flex flex-column gap-2">
        {orderIds.map((id) => {
          const order = ordersById[id];
          if (!order) return null;

          return (
            <OrderCard
              key={id}
              order={order}
              columnId={column.id}
            />
          );
        })}
      </div>
    </div>
  );
}
