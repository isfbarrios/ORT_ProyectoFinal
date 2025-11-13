// src/components/dashboard/Column.jsx
import { useDispatch, useSelector } from "react-redux";
import { moveOrder } from "../redux/features/dashboardSlice";

import OrderCard from "./OrderCard";

export default function Column({ column }) {
  const dispatch = useDispatch();
  const { orders } = useSelector((state) => state.dashboard);

  const handleDrop = (e) => {
    e.preventDefault();

    const orderId = e.dataTransfer.getData("orderId");
    const sourceColId = e.dataTransfer.getData("sourceColId");

    if (!orderId || !sourceColId) return;

    // Calcular posición aproximada
    const rect = e.currentTarget.getBoundingClientRect();
    const mouseY = e.clientY - rect.top;
    const cardHeight = 80;
    const index = Math.floor(mouseY / cardHeight);

    dispatch(
      moveOrder({
        orderId: Number(orderId),
        sourceColId,
        targetColId: column.id,
        targetIndex: index,
      })
    );
  };

  return (
    <div
      className="bg-light p-2 rounded shadow-sm"
      style={{ width: "280px", minHeight: "70vh" }}
      onDragOver={(e) => e.preventDefault()}
      onDrop={handleDrop}
    >
      <h5 className="mb-3 text-center">{column.title}</h5>

      <div className="d-flex flex-column gap-2">
        {column.orderIds.map((id) => (
          <OrderCard key={id} order={orders[id]} columnId={column.id} />
        ))}
      </div>
    </div>
  );
}
