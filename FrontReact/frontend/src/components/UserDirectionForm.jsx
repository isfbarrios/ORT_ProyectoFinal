import {
  Button,
  FormControl,
  FormLabel,
  Input,
  Textarea,
  VStack,
} from "@chakra-ui/react";

export default function UserDirectionForm({
  formData,
  onChange,
  onSubmit,
  loading,
}) {
  return (
    <form onSubmit={onSubmit}>
      <VStack spacing={4} align="stretch">
        <FormControl isRequired>
          <FormLabel>Calle</FormLabel>
          <Input
            type="text"
            name="streetName"
            value={formData.streetName}
            onChange={onChange}
          />
        </FormControl>

        <FormControl isRequired>
          <FormLabel>Número</FormLabel>
          <Input
            type="text"
            name="doorNumber"
            value={formData.doorNumber}
            onChange={onChange}
          />
        </FormControl>

        <FormControl isRequired>
          <FormLabel>Teléfono</FormLabel>
          <Input
            type="text"
            name="phone"
            value={formData.phone}
            onChange={onChange}
          />
        </FormControl>

        <FormControl>
          <FormLabel>Comentarios</FormLabel>
          <Textarea
            name="comments"
            value={formData.comments}
            onChange={onChange}
            placeholder="Aclaraciones para la entrega"
          />
        </FormControl>

        <Button type="submit" colorScheme="orange" isLoading={loading}>
          Guardar dirección
        </Button>
      </VStack>
    </form>
  );
}
