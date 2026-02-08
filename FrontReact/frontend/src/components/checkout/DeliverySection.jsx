import {
  Divider,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Link,
  Select,
  Text,
  Textarea,
} from "@chakra-ui/react";
import { Link as RouterLink } from "react-router-dom";

export default function DeliverySection({
  deliveryMode,
  setDeliveryMode,
  selectedDirection,
  setSelectedDirection,
  directionOptions,
  directionsLoading,
  directionsError,
  phone,
  setPhone,
  comments,
  setComments,
}) {
  return (
    <>
      <Heading size="md">Entrega</Heading>

      <FormControl>
        <FormLabel>Tipo de entrega</FormLabel>
        <Select
          value={deliveryMode}
          onChange={(e) => setDeliveryMode(e.target.value)}
          bg="orange.50"
          borderColor="orange.200"
          focusBorderColor="orange.400"
          _hover={{ borderColor: "orange.300" }}
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
          bg="orange.50"
          borderColor="orange.200"
          focusBorderColor="orange.400"
          _hover={{ borderColor: "orange.300" }}
        >
          {directionOptions.map((item) => (
            <option key={item.id} value={String(item.id)}>
              {item.streetName} {item.doorNumber}
            </option>
          ))}
        </Select>
        {directionsLoading && (
          <Text fontSize="sm" color="gray.500" mt={2}>
            Cargando direcciones...
          </Text>
        )}
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
          bg="orange.50"
          borderColor="orange.200"
          focusBorderColor="orange.400"
          _hover={{ borderColor: "orange.300" }}
        />
      </FormControl>

      <FormControl>
        <FormLabel>Comentarios</FormLabel>
        <Textarea
          value={comments}
          onChange={(e) => setComments(e.target.value)}
          placeholder="Aclaraciones para la entrega"
          bg="orange.50"
          borderColor="orange.200"
          focusBorderColor="orange.400"
          _hover={{ borderColor: "orange.300" }}
        />
      </FormControl>

      <Divider />
    </>
  );
}
