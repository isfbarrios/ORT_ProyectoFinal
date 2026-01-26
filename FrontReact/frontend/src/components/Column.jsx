import { useSelector } from "react-redux";
import { useDroppable } from "@dnd-kit/core";
import { SortableContext, verticalListSortingStrategy } from "@dnd-kit/sortable";
import { Chip, Paper, Stack, Typography } from "@mui/material";
import OrderCard from "./OrderCard";

const STATUS_STYLES = {
  1: { label: "Pendiente", color: "#DC2626", chipBg: "#FEE2E2" },
  2: { label: "Preparacion", color: "#FB923C", chipBg: "#FFF7ED" },
  3: { label: "Listo", color: "#22C55E", chipBg: "#ECFDF3" },
  4: { label: "Entregado", color: "#9CA3AF", chipBg: "#F3F4F6" },
};

export default function Column({ column }) {
  const ordersById = useSelector((state) => state.dashboard.orders);
  const { setNodeRef, isOver } = useDroppable({
    id: `column-${column.id}`,
    data: { columnId: column.id },
  });

  const orderIds = column.orderIds || [];
  const statusStyle = STATUS_STYLES[column.id] || STATUS_STYLES[1];

  return (
    <Paper
      ref={setNodeRef}
      elevation={2}
      sx={{
        width: 300,
        minHeight: "70vh",
        p: 2,
        bgcolor: "common.white",
        border: "1px solid",
        borderColor: statusStyle.color,
        boxShadow: "0 12px 30px rgba(245, 124, 0, 0.08)",
      }}
    >
      <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ mb: 2 }}>
        <Typography variant="h6" fontWeight="bold" sx={{ color: statusStyle.color }}>
          {column.title}
        </Typography>
        <Chip
          size="small"
          label={orderIds.length}
          sx={{ bgcolor: statusStyle.chipBg, color: statusStyle.color, fontWeight: 600 }}
        />
      </Stack>

      <SortableContext items={orderIds} strategy={verticalListSortingStrategy}>
        <Stack
          spacing={2}
          sx={{
            minHeight: 40,
            borderRadius: 1,
            border: "1px dashed",
            borderColor: isOver ? "orange.400" : "transparent",
            p: 0.75,
            transition: "border-color 0.2s ease",
          }}
        >
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
        </Stack>
      </SortableContext>
    </Paper>
  );
}
