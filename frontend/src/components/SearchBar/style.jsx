import { styled } from 'styled-components';
import SearchIcon from '../../../assets/icons/search.svg';

export const SearchBar = styled.div`
  display: flex;
  height: 32px;
  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.gray[200]};
  border-radius: 2px;
  box-sizing: border-box;
  overflow: hidden;
`;

export const SearchInput = styled.input`
  width: 308px;
  padding: 4px 16px;
  box-sizing: border-box;
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.gray[900]};

  &::placeholder {
    color: ${({ theme }) => theme.color.gray[600]};
  }
`;

export const SearchButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  width: 68px;
  height: 100%;
  border-left: 1px solid ${({ theme }) => theme.color.gray[200]};
  background-color: ${({ theme }) => theme.color.gray[100]};
  /* background-image: url(${SearchIcon});
  background-repeat: no-repeat;
  background-position: center;
  background-size: 18px 18px; */
  cursor: pointer;

  &::before {
    content: '';
    position: absolute;
    left: 0;
    width: 68px;
    height: 100%;
    background-color: ${({ theme }) => theme.color.gray['700']};
    -webkit-mask-image: url(${SearchIcon});
    mask-image: url(${SearchIcon});
    -webkit-mask-repeat: no-repeat;
    mask-repeat: no-repeat;
    -webkit-mask-position: center;
    mask-position: center;
    -webkit-mask-size: 18px 18px;
    mask-size: 18px 18px;
  }
`;
