import {
  Box,
  Heading,
  IconButton,
  Stack,
  Table,
  TableContainer,
  Tbody,
  Td,
  Text,
  Th,
  Thead,
  Tr,
  VStack,
} from "@chakra-ui/react";
import { CloseIcon } from "@chakra-ui/icons";

export default function UserDirectionsList({ directions, onDelete }) {
  return (
    <Box
      bg="white"
      borderWidth="1px"
      borderColor="orange.100"
      rounded="xl"
      p={{ base: 4, md: 6 }}
      boxShadow="sm"
    >
      <Heading size="md" mb={4}>
        Mis direcciones
      </Heading>

      {directions.length === 0 && (
        <Text color="gray.500">No tenés direcciones guardadas.</Text>
      )}

      {directions.length > 0 && (
        <>
          <VStack
            spacing={3}
            align="stretch"
            display={{ base: "flex", md: "none" }}
          >
            {directions.map((direction) => (
              <Box
                key={direction.id}
                borderWidth="1px"
                borderColor="orange.100"
                rounded="lg"
                p={4}
              >
                <Stack spacing={1}>
                  <Text fontSize="sm">
                    <strong>Calle:</strong> {direction.streetName}
                  </Text>
                  <Text fontSize="sm">
                    <strong>Puerta:</strong> {direction.doorNumber}
                  </Text>
                  <Text fontSize="sm">
                    <strong>Teléfono:</strong> {direction.phone}
                  </Text>
                  <Text fontSize="sm">
                    <strong>Comentarios:</strong> {direction.comments || "—"}
                  </Text>
                </Stack>
                <Box mt={2} textAlign="right">
                  <IconButton
                    aria-label="Eliminar dirección"
                    icon={<CloseIcon />}
                    size="sm"
                    variant="ghost"
                    colorScheme="red"
                    onClick={() => onDelete(direction.id)}
                  />
                </Box>
              </Box>
            ))}
          </VStack>

          <TableContainer
            width="100%"
            overflowX="auto"
            display={{ base: "none", md: "block" }}
          >
            <Table size="sm" width="100%">
              <Thead>
                <Tr>
                  <Th>Calle</Th>
                  <Th>Puerta</Th>
                  <Th>Teléfono</Th>
                  <Th>Comentarios</Th>
                  <Th textAlign="right"></Th>
                </Tr>
              </Thead>
              <Tbody>
                {directions.map((direction) => (
                  <Tr key={direction.id}>
                    <Td>{direction.streetName}</Td>
                    <Td>{direction.doorNumber}</Td>
                    <Td>{direction.phone}</Td>
                    <Td>{direction.comments}</Td>
                    <Td textAlign="right">
                      <IconButton
                        aria-label="Eliminar dirección"
                        icon={<CloseIcon />}
                        size="sm"
                        variant="ghost"
                        colorScheme="red"
                        onClick={() => onDelete(direction.id)}
                      />
                    </Td>
                  </Tr>
                ))}
              </Tbody>
            </Table>
          </TableContainer>
        </>
      )}
    </Box>
  );
}
