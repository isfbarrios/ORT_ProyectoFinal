import Swal from "sweetalert2";
import "sweetalert2/dist/sweetalert2.min.css";
import { fetchOrderServiceFromApi } from "../../services/orderService";
import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { Button, Card, CardContent, Chip, Stack, Typography } from "@mui/material";

export default function OrderCard({ order, columnId }) {
  const statusStyle =
    {
      1: { label: "Pendiente", color: "#DC2626", chipBg: "#FEE2E2" },
      2: { label: "Preparacion", color: "#FB923C", chipBg: "#FFF7ED" },
      3: { label: "Listo", color: "#22C55E", chipBg: "#ECFDF3" },
      4: { label: "Entregado", color: "#9CA3AF", chipBg: "#F3F4F6" },
    }[Number(columnId)] || { label: "Pendiente", color: "#DC2626", chipBg: "#FEE2E2" };

  const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({
    id: order.id,
    data: { orderId: order.id, columnId },
  });

  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
  };

  const mesa = order.table?.name ?? order.cart?.table?.name ?? "—";
  const estimatedMinutes = Number(order.delayTime) || 0;

  const handleDetails = async () => {
    try {
      Swal.fire({
        title: "Cargando detalles...",
        didOpen: () => Swal.showLoading(),
        allowOutsideClick: false,
      });

      const items = await fetchOrderServiceFromApi(order.cart.id);
      const total = items.reduce((acc, item) => acc + item.itemAmount * item.quantity, 0);
      const estimatedFromItems = items.reduce((max, item) => Math.max(max, item.delayTime ?? 0), 0);
      const estimated = order.delayTime != null ? order.delayTime : estimatedFromItems;

      const rowsHtml = items
        .map((item) => {
          const nombre = item.menuItem?.name ?? "-";
          const cantidad = item.quantity;
          const unit = item.itemAmount;
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
        ${estimated > 0
          ? `<p><strong>Tiempo estimado:</strong> ${estimated} min</p>`
          : `<p><strong>Tiempo estimado:</strong> No definido</p>`}
        <hr />
        ${items.length === 0
          ? "<p class='text-muted'>Este pedido no tiene ítems.</p>"
          : `
          <div class="table-responsive">
            <table class="table table-sm align-middle text-start swal-brand-table">
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
        `}
      `;

      Swal.fire({
        title: `Pedido #${order.id}`,
        html,
        width: 700,
        customClass: {
          popup: "swal-brand-popup",
          confirmButton: "swal-brand-button",
        },
        buttonsStyling: false,
        confirmButtonText: "Cerrar",
      });
    } catch (error) {
      Swal.fire({
        icon: "error",
        title: "Error",
        text: error.message || "No se pudieron cargar los detalles",
      });
    }
  };

  return (
    <Card
      ref={setNodeRef}
      variant="outlined"
      sx={{
        cursor: "grab",
        minHeight: 80,
        opacity: isDragging ? 0.6 : 1,
        boxShadow: isDragging ? 3 : 1,
        borderColor: "transparent",
        bgcolor: statusStyle.chipBg,
        transition: "transform 120ms ease, box-shadow 120ms ease",
        "&:hover": {
          transform: "translateY(-2px)",
          boxShadow: 3,
        },
        ...style,
      }}
      {...listeners}
      {...attributes}
    >
      <CardContent sx={{ pb: 2 }}>
        <Stack spacing={0.5}>
          <Stack direction="row" alignItems="center" justifyContent="space-between">
            <Typography variant="subtitle1" fontWeight="bold">
              #{order.id}
            </Typography>
            <Stack direction="row" spacing={0.5} alignItems="center">
              {estimatedMinutes > 0 && (
                <Chip
                  size="small"
                  label={`${estimatedMinutes} min`}
                  sx={{ bgcolor: "#FFF7ED", color: "#9A3412", fontWeight: 600 }}
                />
              )}
              <Chip
                size="small"
                label={statusStyle.label}
                sx={{ bgcolor: statusStyle.chipBg, color: statusStyle.color, fontWeight: 600 }}
              />
            </Stack>
          </Stack>
          <Typography variant="body2" color="text.secondary">
            Mesa: {mesa}
          </Typography>
          <Button
            size="small"
            variant="outlined"
            onClick={handleDetails}
            onPointerDown={(event) => event.stopPropagation()}
          >
            Ver detalles
          </Button>
        </Stack>
      </CardContent>
    </Card>
  );
}
