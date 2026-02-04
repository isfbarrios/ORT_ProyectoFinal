import { useEffect, useMemo, useRef, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useLocation } from "react-router-dom";
import { Button, Heading, Stack, Text, VStack, Card, CardBody, CardFooter } from "@chakra-ui/react";
import { getUserDirections } from "../services/userDirectionService";
import DeliverySection from "../components/checkout/DeliverySection";
import PaymentSection from "../components/checkout/PaymentSection";
import OrderSummaryCard from "../components/checkout/OrderSummaryCard";
import BillSummaryCard from "../components/checkout/BillSummaryCard";
import { USER_TYPE, getFromLocalStorage } from "../functions/localStorage";
import { closeCartAsync } from "../redux/features/cartSlice";


export default function Checkout() {
  const dispatch = useDispatch();
  const { items, totalAmount, cartId } = useSelector((state) => state.cart);
  const direction = useSelector((state) => state.userDirection.direction);
  const location = useLocation();
  const userType = getFromLocalStorage(USER_TYPE);
  const isLocal = userType === "LOCAL";
  const billFromLocation = location.state?.bill || null;

  const [deliveryMode, setDeliveryMode] = useState(isLocal ? "LOCAL" : "DELIVERY");
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
  const [bill, setBill] = useState(billFromLocation);
  const [summaryItems, setSummaryItems] = useState(items);
  const [summaryTotal, setSummaryTotal] = useState(totalAmount);
  const hasCreatedBillRef = useRef(false);

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

  useEffect(() => {
    if (items.length > 0 && summaryItems.length === 0) {
      setSummaryItems(items);
    }
    if (totalAmount > 0 && summaryTotal === 0) {
      setSummaryTotal(totalAmount);
    }
  }, [items, summaryItems.length, totalAmount, summaryTotal]);

  useEffect(() => {
    if (hasCreatedBillRef.current || bill) return;
    if (!cartId) return;

    const payload = {
      cartId,
      deliveryMode,
      paymentMethod,
      phone,
      comments,
      directionId: deliveryMode === "LOCAL" ? -1 : selectedDirection || -1,
    };

    hasCreatedBillRef.current = true;

    dispatch(closeCartAsync(payload)).then((data) => {
      if (data?.billNumber) {
        setBill(data);
      }
    });
  }, [
    bill,
    cartId,
    comments,
    deliveryMode,
    dispatch,
    paymentMethod,
    phone,
    selectedDirection,
  ]);

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
              {!isLocal && (
                <DeliverySection
                  deliveryMode={deliveryMode}
                  setDeliveryMode={setDeliveryMode}
                  selectedDirection={selectedDirection}
                  setSelectedDirection={setSelectedDirection}
                  directionOptions={directionOptions}
                  directionsLoading={directionsLoading}
                  directionsError={directionsError}
                  phone={phone}
                  setPhone={setPhone}
                  comments={comments}
                  setComments={setComments}
                />
              )}

              <PaymentSection
                paymentMethod={paymentMethod}
                setPaymentMethod={setPaymentMethod}
              />
            </VStack>
          </CardBody>
          <CardFooter>
            <Button colorScheme="orange" onClick={handleSubmit}>
              Pagar
            </Button>
          </CardFooter>
        </Card>
        <VStack spacing={4} align="stretch">
          <BillSummaryCard bill={bill} />
          {!isLocal && (
            <OrderSummaryCard items={summaryItems} totalAmount={summaryTotal} />
          )}
        </VStack>
      </Stack>
    </Stack>
  );
}
