import { TsResult, TimeRangeUnit } from '@/types/graphql'

type DataSets = TsResult[][]

interface MetricArgs {
  name: string,
  monitor: string, // ICMP
  id?: number,
  instance?: string!
  timeRange: number,
  timeRangeUnit: TimeRangeUnit
}

interface GraphProps {
  label: string!,
  metrics: string[],
  monitor: string, // ICMP
  id?: number,
  instance?: string!
  timeRange: number,
  timeRangeUnit: TimeRangeUnit
}
 