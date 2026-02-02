import {
  Card,
  CardBody,
  Divider,
  Heading,
  HStack,
  Text,
  VStack,
} from "@chakra-ui/react";

export default function BillSummaryCard({ bill }) {
  return (
    <Card w={{ base: "100%", lg: "360px" }} borderWidth="1px">
      <CardBody>
        <Heading size="md" mb={4}>
          Boleta
        </Heading>
        {!bill && (
          <Text color="gray.500">No hay boleta disponible.</Text>
        )}
        {bill && (
          <VStack spacing={3} align="stretch">
            <HStack justify="space-between">
              <Text color="gray.500">Número</Text>
              <Text fontWeight="semibold">{bill.billNumber}</Text>
            </HStack>
            <HStack justify="space-between">
              <Text color="gray.500">Importe</Text>
              <Text fontWeight="semibold">${bill.amount}</Text>
            </HStack>
            <Divider />
            <HStack justify="space-between">
              <Text color="gray.500">Fecha</Text>
              <Text fontWeight="semibold">{bill.date}</Text>
            </HStack>
          </VStack>
        )}
      </CardBody>
    </Card>
  );
}
