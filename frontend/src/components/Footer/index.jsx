import * as S from './style';
import { useData } from '../../utils/Context';

function Footer() {
  const { page } = useData();
  if (page !== 1 && page !== 6) return <S.Footer />;
}

export default Footer;
