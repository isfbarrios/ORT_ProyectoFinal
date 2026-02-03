import { FormControl, FormLabel, Heading, Select } from "@chakra-ui/react";

export default function PaymentSection({
  paymentMethod,
  setPaymentMethod,
}) {
  return (
    <>
      <Heading size="md">Pago</Heading>
      <FormControl isRequired>
        <FormLabel>Método de pago</FormLabel>
        <Select
          placeholder="Seleccionar"
          value={paymentMethod}
          onChange={(e) => setPaymentMethod(e.target.value)}
        >
          <option value="1">Efectivo</option>
          <option value="3">Mercado Pago</option>
        </Select>
      </FormControl>
    </>
  );
}
