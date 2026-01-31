import { useEffect, useMemo, useState } from "react";
import { useSelector } from "react-redux";
import { Link as RouterLink } from "react-router-dom";
import {
  Box,
  Button,
  Divider,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Select,
  Stack,
  Text,
  Textarea,
  VStack,
  Card,
  CardBody,
  CardFooter,
  Badge,
  Link,
} from "@chakra-ui/react";
import { getUserDirections } from "../services/userDirectionService";

export default function Checkout() {
  const { items, totalAmount } = useSelector((state) => state.cart);
  const direction = useSelector((state) => state.userDirection.direction);

  const [deliveryMode, setDeliveryMode] = useState("DELIVERY");
  const [paymentMethod, setPaymentMethod] = useState("");
  const [phone, setPhone] = useState("");
  const [comments, setComments] = useState("");
  // Dirección preseleccionada si ya existe en el store.
  const [selectedDirection, setSelectedDirection] = useState(
    direction?.id ? String(direction.id) : ""
  );
  // Lista de direcciones del API y estados de carga/errores.
  const [directions, setDirections] = useState([]);
  const [directionsLoading, setDirectionsLoading] = useState(false);
  const [directionsError, setDirectionsError] = useState("");

  useEffect(() => {
    let isMounted = true;
    const loadDirections = async () => {
      // Carga inicial de direcciones del usuario.
      setDirectionsLoading(true);
      setDirectionsError("");
      try {
        // Pido al backend las direcciones del usuario.
        const data = await getUserDirections();
        console.log("getUserDirections raw:", data);
        // respuesta a array.
        const list = Array.isArray(data?.directions) ? data.directions : [];
        console.log("getUserDirections list:", list);
        if (isMounted) {
          // Guarda la lista para renderizar en el select.
          setDirections(list);
        }
      }
      catch (error) {
        console.error("getUserDirections error:", error);

        if (isMounted) {
          // Muestra error si falla la carga.
          setDirectionsError("No se pudieron cargar las direcciones.");
        }
      }
      finally {
        if (isMounted) {
          // Apaga el loading cuando termina la llamada.
          setDirectionsLoading(false);
        }
      }
    };

    loadDirections();

    return () => {
      // Evita setState si el usuario sale de la pantalla.
      isMounted = false;
    };
  }, []);

  const directionOptions = useMemo(() => {
    // Evita duplicados entre API y store (mismo id).
    const map = new Map();
    directions.forEach((item) => {
      if (item?.id != null) {
        map.set(String(item.id), item);
      }
    });
    if (direction?.id != null && !map.has(String(direction.id))) {
      map.set(String(direction.id), direction);
    }
    return Array.from(map.values());
  }, [directions, direction]);

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = {
      deliveryMode,
      paymentMethod,
      phone,
      comments,
      // En retiro no se envía dirección.
      directionId: deliveryMode === "PICKUP" ? null : selectedDirection || null,
    };
    console.log("checkout payload", payload);
  };

  return (
    <Stack spacing={6}>
      <VStack spacing={2} textAlign="center">
        <Heading size="lg">Confirmar pedido</Heading>
        <Text color="gray.500">
          Elegí dirección, método de pago y cómo querés recibirlo.
        </Text>
      </VStack>

      <Stack direction={{ base: "column", lg: "row" }} spacing={6}>
        <Card flex="1" borderWidth="1px" borderColor="orange.100">
          <CardBody>
            <VStack spacing={4} align="stretch">
              <Heading size="md">Entrega</Heading>

              <FormControl>
                <FormLabel>Tipo de entrega</FormLabel>
                <Select
                  value={deliveryMode}
                  onChange={(e) => setDeliveryMode(e.target.value)}
                >
                  <option value="DELIVERY">Envío a domicilio</option>
                  <option value="PICKUP">Retiro en local</option>
                </Select>
              </FormControl>

              <FormControl>
                <FormLabel>Dirección</FormLabel>
                <Select
                  placeholder="Seleccionar dirección"
                  value={selectedDirection}
                  onChange={(e) => setSelectedDirection(e.target.value)}
                  isDisabled={deliveryMode === "PICKUP"}
                >
                  {/* Carga las opciones desde el listado de direcciones ya normalizado */}
                  {directionOptions.map((item) => (
                    <option key={item.id} value={String(item.id)}>
                      {/* Texto visible del select */}
                      {item.streetName} {item.doorNumber}
                    </option>
                  ))}
                </Select>
                {/* Indicador de carga mientras llega el fetch */}
                {directionsLoading && (
                  <Text fontSize="sm" color="gray.500" mt={2}>
                    Cargando direcciones...
                  </Text>
                )}
                {/* Error si no se pudo traer la lista */}
                {!directionsLoading && directionsError && (
                  <Text fontSize="sm" color="red.500" mt={2}>
                    {directionsError}
                  </Text>
                )}
                <Text fontSize="sm" color="gray.500" mt={2}>
                  ¿Necesitás otra?{" "}
                  <Link as={RouterLink} to="/add_direction" color="orange.500">
                    Agregar dirección
                  </Link>
                </Text>
              </FormControl>

              <FormControl isRequired>
                <FormLabel>Teléfono de contacto</FormLabel>
                <Input
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                  placeholder="Ej: 11 1234 5678"
                />
              </FormControl>

              <FormControl>
                <FormLabel>Comentarios</FormLabel>
                <Textarea
                  value={comments}
                  onChange={(e) => setComments(e.target.value)}
                  placeholder="Aclaraciones para la entrega"
                />
              </FormControl>

              <Divider />

              <Heading size="md">Pago</Heading>
              <FormControl isRequired>
                <FormLabel>Método de pago</FormLabel>
                <Select
                  placeholder="Seleccionar"
                  value={paymentMethod}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                >
                  <option value="CASH">Efectivo</option>
                  <option value="MERCADO_PAGO">Mercado Pago</option>
                </Select>
              </FormControl>
            </VStack>
          </CardBody>
          <CardFooter>
            <Button colorScheme="orange" onClick={handleSubmit}>
              Confirmar pedido
            </Button>
          </CardFooter>
        </Card>

        <Card w={{ base: "100%", lg: "360px" }} borderWidth="1px">
          <CardBody>
            <Heading size="md" mb={4}>
              Resumen
            </Heading>
            <VStack spacing={3} align="stretch">
              {items.map((item) => (
                <HStack key={item.cartItemId} justify="space-between">
                  <Box>
                    <Text fontWeight="semibold">{item.name}</Text>
                    <Text fontSize="sm" color="gray.500">
                      x{item.quantity}
                    </Text>
                  </Box>
                  <Text fontWeight="semibold">
                    ${Number(item.unitPrice) * item.quantity}
                  </Text>
                </HStack>
              ))}
              {items.length === 0 && (
                <Text color="gray.500">No hay productos en el carrito.</Text>
              )}
              <Divider />
              <HStack justify="space-between">
                <Text fontWeight="bold">Total</Text>
                <Text fontWeight="bold">${totalAmount}</Text>
              </HStack>
            </VStack>
          </CardBody>
        </Card>
      </Stack>
    </Stack>
  );
}