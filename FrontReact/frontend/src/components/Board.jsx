import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { DndContext, DragOverlay, closestCenter } from "@dnd-kit/core";
import { arrayMove } from "@dnd-kit/sortable";
import { Box, Card, CardContent, Chip, Stack, Typography } from "@mui/material";
import Swal from "sweetalert2";
import Column from "./Column";
import {
  moveOrder,
  reorderColumn,
  syncOrderStatus,
} from "../redux/features/dashboardSlice";

export default function Board() {
  const dispatch = useDispatch();
  const { columns, orders } = useSelector((state) => state.dashboard);
  const [activeOrderId, setActiveOrderId] = useState(null);

  if (!columns || columns.length === 0)
    return <p>No hay columnas cargadas...</p>;

  const getTargetColumnId = (over) => {
    if (!over) return null;
    if (typeof over.id === "string" && over.id.startsWith("column-")) {
      return Number(over.id.replace("column-", ""));
    }
    return over.data?.current?.columnId ?? null;
  };

  const handleDragStart = ({ active }) => {
    setActiveOrderId(active.data?.current?.orderId ?? null);
  };

  const handleDragCancel = () => {
    setActiveOrderId(null);
  };

  const handleDragEnd = async ({ active, over }) => {
    const orderId = active.data?.current?.orderId;
    const sourceColId = active.data?.current?.columnId;
    const targetColId = getTargetColumnId(over);

    if (!orderId || !sourceColId || !targetColId) {
      setActiveOrderId(null);
      return;
    }

    if (sourceColId === targetColId) {
      const column = columns.find((col) => Number(col.id) === sourceColId);
      if (!column) {
        setActiveOrderId(null);
        return;
      }
      const orderIds = column.orderIds || [];
      const oldIndex = orderIds.indexOf(orderId);
      const overId = over?.id;
      const newIndex =
        typeof overId === "string" && overId.startsWith("column-")
          ? oldIndex
          : orderIds.indexOf(overId);
      if (oldIndex < 0 || newIndex < 0 || oldIndex === newIndex) {
        setActiveOrderId(null);
        return;
      }

      const reordered = arrayMove(orderIds, oldIndex, newIndex);
      dispatch(reorderColumn({ columnId: sourceColId, orderIds: reordered }));
      setActiveOrderId(null);
      return;
    }

    if (Math.abs(Number(targetColId) - Number(sourceColId)) > 1) {
      Swal.fire({
        icon: "info",
        title: "Movimiento no permitido",
        text: "El pedido debe pasar por el estado siguiente.",
      });
      setActiveOrderId(null);
      return;
    }

    dispatch(
      moveOrder({
        orderId,
        sourceColId,
        targetColId,
        targetIndex:
          typeof over?.id === "string" && over.id.startsWith("column-")
            ? undefined
            : (columns
                .find((col) => Number(col.id) === targetColId)
                ?.orderIds || []
              ).indexOf(over.id),
      })
    );

    try {
      await dispatch(syncOrderStatus({ orderId, targetColId }));
    } catch (error) {
      dispatch(
        moveOrder({
          orderId,
          sourceColId: targetColId,
          targetColId: sourceColId,
        })
      );

      Swal.fire({
        icon: "error",
        title: "Error",
        text: "No se pudo actualizar el estado del pedido",
      });
    }

    setActiveOrderId(null);
  };

  const activeOrder = activeOrderId ? orders[activeOrderId] : null;
  const activeColumnId = activeOrder?.state?.id;
  const activeStatusStyle =
    {
      1: { label: "Pendiente", color: "#DC2626", chipBg: "#FEE2E2" },
      2: { label: "Preparacion", color: "#FB923C", chipBg: "#FFF7ED" },
      3: { label: "Listo", color: "#22C55E", chipBg: "#ECFDF3" },
      4: { label: "Entregado", color: "#9CA3AF", chipBg: "#F3F4F6" },
    }[Number(activeColumnId)] || { label: "Pendiente", color: "#DC2626", chipBg: "#FEE2E2" };
  const activeEstimatedMinutes = Number(activeOrder?.delayTime) || 0;

  return (
    <DndContext
      onDragStart={handleDragStart}
      onDragCancel={handleDragCancel}
      onDragEnd={handleDragEnd}
      collisionDetection={closestCenter}
    >
      <Box
        sx={{
          minHeight: "80vh",
          overflowX: "auto",
          p: 3,
          background:
            "linear-gradient(180deg, rgba(255, 248, 240, 1) 0%, rgba(255, 255, 255, 1) 60%)",
        }}
      >
        <Stack direction="row" spacing={3} sx={{ minWidth: "fit-content" }}>
          {columns.map((col) => (
            <Column key={col.id} column={col} />
          ))}
        </Stack>
      </Box>

      <DragOverlay>
        {activeOrder ? (
          <Card
            variant="outlined"
            sx={{
              minWidth: 260,
              borderColor: activeStatusStyle.color,
              boxShadow: "0 10px 24px rgba(17, 24, 39, 0.12)",
            }}
          >
            <CardContent sx={{ pb: 2 }}>
              <Stack spacing={0.5}>
                <Stack direction="row" alignItems="center" justifyContent="space-between">
                  <Typography variant="subtitle1" fontWeight="bold">
                    #{activeOrder.id}
                  </Typography>
                  <Stack direction="row" spacing={0.5} alignItems="center">
                    {activeEstimatedMinutes > 0 && (
                      <Chip
                        size="small"
                        label={`${activeEstimatedMinutes} min`}
                        sx={{ bgcolor: "#FFF7ED", color: "#9A3412", fontWeight: 600 }}
                      />
                    )}
                    <Chip
                      size="small"
                      label={activeStatusStyle.label}
                      sx={{
                        bgcolor: activeStatusStyle.chipBg,
                        color: activeStatusStyle.color,
                        fontWeight: 600,
                      }}
                    />
                  </Stack>
                </Stack>
                <Typography variant="body2" color="text.secondary">
                  Mesa: {activeOrder.table?.name ?? "—"}
                </Typography>
              </Stack>
            </CardContent>
          </Card>
        ) : null}
      </DragOverlay>
    </DndContext>
  );
}
